package controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import controller.exception.DatabaseException;
import controller.exception.UnauthorizedException;
import java.time.DayOfWeek;
import dao.ChangeOfDateReqDao;
import dao.LectureDao;
import implementazioneDao.exception.DataInsertionException;
import model.ChangeOfDateReq;
import model.Lecture;

import implementazioneDao.ChangeOfDateReqPostgresDao;

public class ChangeOfDateReqService extends AbstractDaoService<ChangeOfDateReqDao> {
	public ChangeOfDateReqService() {
		super(new ChangeOfDateReqPostgresDao());
	}

	/**
	 * Metodo per creare una richiesta di spostamento lezione
	 *
	 * @throws UnauthorizedException se l'utente non è un docente.
	 * @throws DatabaseException     se il DB fallisce.
	 */
	public void makeChangeOfDateReq(int lectureId, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime) {

		if (!SessionManager.getInstance().isTeacher())
			throw new UnauthorizedException("This operation is restricted to teachers only");

		try {
			dao.insertCodReq(SessionManager.getInstance().getUserId(), lectureId, newDow, newStartTime,
					newEndTime);
		} catch (DataInsertionException e) {
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

		dao.changeStatusOfCODR(SessionManager.getInstance().getUserId(), reqId, isApproved);

		if (!isApproved)
			return;

		// TODO:
		ChangeOfDateReq codr = dao.getById(reqId);

		LectureDao.getInstance().changeLectureDate(codr.getLecture().getId(), codr.getNewDow(),
				codr.getNewStartTime(), codr.getNewEndTime());
	}

	/**
	 * @return Lista di stringhe con le informazioni identificative delle
	 *         ChangeOfDateReq con status WAITING
	 * @throws UnauthorizedException se l'utente non è un coordinatore.
	 */
	public List<String> getWaitingCODRInfo() throws UnauthorizedException {
		if (!SessionManager.getInstance().isCoordinator())
			throw new UnauthorizedException("getWaitingCODRInfo() is restricted to coordinators only");

		List<ChangeOfDateReq> codrs;

		codrs = dao.getAllWaiting();

		List<String> codrInfo = new ArrayList<>();
		for (ChangeOfDateReq codr : codrs) {
			codrInfo.add(codr.getId() + " Professore: " +
					codr.getAskingTeacher().getFname() + " " + codr.getAskingTeacher().getLname());
		}
		return codrInfo;
	}

	public ChangeOfDateReq getCODRbyId(int reqId) throws UnauthorizedException {
		if (!SessionManager.getInstance().isCoordinator())
			throw new UnauthorizedException("getCODRbyId() is restricted to coordinators only");
		return dao.getById(reqId);
	}

	public String getCODROldTimeAndDate(int reqId) throws UnauthorizedException {
		Lecture lecture = LectureDao.getInstance().getById(getCODRbyId(reqId).getLecture().getId());
		return lecture.getDayofweek().toString() + " " + lecture.getTimeInterval();
	}

	public String getCODRNewTimeAndDate(int reqId) throws UnauthorizedException {
		ChangeOfDateReq codr = getCODRbyId(reqId);
		return codr.getNewDow().toString() + " " +
				codr.getNewStartTime().toString() + " - " + codr.getNewEndTime().toString();
	}
}
