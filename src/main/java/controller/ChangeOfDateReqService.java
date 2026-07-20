package controller;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import controller.exception.DatabaseException;
import controller.exception.UnauthorizedException;

import java.time.DayOfWeek;
import dao.ChangeOfDateReqDao;
import dao.LectureDao;
import model.ChangeOfDateReq;

public class ChangeOfDateReqService {
	private final ChangeOfDateReqDao cdao = ChangeOfDateReqDao.getInstance();

	/**
	 * Metodo per creare una richiesta di spostamento lezione
	 *
	 * @throws UnauthorizedException se l'utente non è un docente.
	 * @throws DatabaseException     se il DB fallisce.
	 */
	public void makeChangeOfDateReq(int lectureId, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime)
			throws RuntimeException {

		if (!SessionManager.getInstance().isTeacher())
			throw new UnauthorizedException("This operation is restricted to teachers only");

		try {
			cdao.insertCodReq(SessionManager.getInstance().getSession().getUserId(), lectureId, newDow, newStartTime,
					newEndTime);
		} catch (SQLException e) {
			throw new DatabaseException("unable to make change of date request of lecture with id " + lectureId, e);
		}

	}

	/**
	 * Metodo per approvare una richiesta di spostamento lezione
	 *
	 * @throws UnauthorizedException se l'utente non è un coordinatore.
	 * @throws DatabaseException     se il DB fallisce.
	 */
	public void changeStatusOfCODR(int reqId, boolean isApproved) {
		if (!SessionManager.getInstance().isCoordinator())
			throw new UnauthorizedException("This operation is restricted to coordinators only");

		try {
			cdao.changeStatusOfCODR(SessionManager.getInstance().getSession().getUserId(), reqId, isApproved);
		} catch (NoSuchElementException e) {
			throw new DatabaseException("unable to set status of change of date request with id " + reqId, e);
		}

		if (!isApproved)
			return;

		ChangeOfDateReq codr = ChangeOfDateReqDao.getInstance().getById(reqId);

		// TODO: dependency nel class diagram verso lectureDao
		try {
			LectureDao.getInstance().changeLectureDate(codr.getLecture().getLectureId(), codr.getNewDow(),
					codr.getNewStartTime(), codr.getNewEndTime());
		} catch (SQLException e) {
			throw new DatabaseException("Unexpected error occurred on call of changeLectureDate in changeStatusOfCODR",
					e);
		}
	}

	public List<ChangeOfDateReq> getChangeOfDateReqs() throws UnsupportedOperationException {
		// TODO: implementazione
		throw new UnsupportedOperationException("TODO");
	}

	public List<String> getCODRInfo() throws IllegalStateException {
		List<ChangeOfDateReq> codrs;

		try {
			codrs = getChangeOfDateReqs();
		} catch (Exception e) {
			throw new IllegalStateException("TEMP. EXCEPTION");
		}

		List<String> codrInfo = new ArrayList<>();
		for (ChangeOfDateReq codr : codrs) {
			codrInfo.add(codr.getReqId() + "Professore: " +
					codr.getAskingTeacher().getFname() + " " + codr.getAskingTeacher().getLname());
		}
		return codrInfo;
	}
}
