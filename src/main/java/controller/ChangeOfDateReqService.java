package controller;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.time.DayOfWeek;
import dao.ChangeOfDateReqDao;

public class ChangeOfDateReqService {
	public void makeChangeOfDateReq(int askingTeacherUid, int lectureId, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime)
			throws RuntimeException {

		if (!SessionManager.getInstance().isTeacher())
			throw new RuntimeException("This operation is restricted to teachers only");

		ChangeOfDateReqDao cdao = ChangeOfDateReqDao.getInstance();
		try {
			cdao.insertCodReq(askingTeacherUid, lectureId, newDow, newStartTime, newEndTime);
		} catch (SQLException e) {
			throw new RuntimeException("unable to make change of date request of lecture with id " + lectureId);
		}

	}

	// TODO: update lecture if approved
	public void changeStatusOfCODR(int reqId, boolean isApproved) throws IllegalStateException {
		if (!SessionManager.getInstance().isCoordinator())
			throw new IllegalStateException("This operation is restricted to coordinators only");

		ChangeOfDateReqDao cdao = ChangeOfDateReqDao.getInstance();
		try {
			cdao.changeStatusOfCODR(SessionManager.getInstance().getSession().getUserId(), reqId, isApproved);
		} catch (NoSuchElementException e) {
			throw new RuntimeException("unable to set status of change of date request with id " + reqId);
		}
	}
}
