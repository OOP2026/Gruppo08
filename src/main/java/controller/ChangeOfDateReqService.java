package controller;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.time.DayOfWeek;
import dao.ChangeOfDateReqDao;
import dao.LectureDao;
import model.ChangeOfDateReq;

public class ChangeOfDateReqService {
	public void makeChangeOfDateReq(int lectureId, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime)
			throws RuntimeException {

		if (!SessionManager.getInstance().isTeacher())
			throw new RuntimeException("This operation is restricted to teachers only");

		ChangeOfDateReqDao cdao = ChangeOfDateReqDao.getInstance();
		try {
			cdao.insertCodReq(SessionManager.getInstance().getSession().getUserId(), lectureId, newDow, newStartTime,
					newEndTime);
		} catch (SQLException e) {
			throw new RuntimeException("unable to make change of date request of lecture with id " + lectureId);
		}

	}

	public void changeStatusOfCODR(int reqId, boolean isApproved) throws IllegalStateException {
		if (!SessionManager.getInstance().isCoordinator())
			throw new IllegalStateException("This operation is restricted to coordinators only");

		ChangeOfDateReqDao cdao = ChangeOfDateReqDao.getInstance();
		try {
			cdao.changeStatusOfCODR(SessionManager.getInstance().getSession().getUserId(), reqId, isApproved);
		} catch (NoSuchElementException e) {
			throw new RuntimeException("unable to set status of change of date request with id " + reqId);
		}

		if (isApproved == false)
			return;

		ChangeOfDateReq codr = ChangeOfDateReqDao.getInstance().getById(reqId);

		// TODO: dependency nel class diagram
		try {
			LectureDao.getInstance().changeLectureDate(codr.getLecture().getLectureId(), codr.getNewDow(),
					codr.getNewStartTime(), codr.getNewEndTime());
		} catch (SQLException e) {
			throw new RuntimeException("Unexpected error occurred on call of changeLectureDate in changeStatusOfCODR");
		}
	}

	public List<ChangeOfDateReq> getChangeOfDateReqs () {}

	public List<String> getCODRInfo() throws IllegalStateException {
		List<ChangeOfDateReq> codrs;

		try {
			codrs = getChangeOfDateReqs();
		} catch (Exception e) {
			throw new IllegalStateException("TEMP. EXCEPTION");
		}

		List<String> codrInfo = new ArrayList();
		for (ChangeOfDateReq codr : codrs) {
			codrInfo.add(codr.getReqId() + "Professore: " +
					codr.getAskingTeacher().getFname() + " " + codr.getAskingTeacher().getLname() );
		}
		return codrInfo;
	}
}
