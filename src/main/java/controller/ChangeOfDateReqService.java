package controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import controller.cache.ChangeOfDateReqCache;
import controller.exception.DatabaseException;
import controller.exception.UnauthorizedException;
import java.time.DayOfWeek;
import dao.ChangeOfDateReqDao;
import implementazioneDao.exception.DataInsertionException;
import model.ChangeOfDateReq;
import model.Lecture;
import model.RequestStatus;
import model.Teacher;
import implementazioneDao.ChangeOfDateReqPostgresDao;
import implementazioneDao.entity.ChangeOfDateReqEntity;

public class ChangeOfDateReqService
		extends AbstractDaoService<ChangeOfDateReq, ChangeOfDateReqEntity, Integer, ChangeOfDateReqDao> {
	public ChangeOfDateReqService() {
		super(new ChangeOfDateReqPostgresDao());
	}

	@Override
	protected ChangeOfDateReq mapEntityToModel(ChangeOfDateReqEntity e) {
		UserAuthentication uauth = new UserAuthentication();
		Teacher askingTeacher = (Teacher) uauth.getById(e.getAskingTeacherUid());
		Teacher reviewingCoord = (Teacher) uauth.getById(e.getReviewingCoordUid());
		LectureService lService = new LectureService();
		Lecture lecture = lService.getById(e.getLectureId());
		return new ChangeOfDateReq(e.getId(), askingTeacher, reviewingCoord, lecture, e.getNewDow(),
				e.getNewStartTime(), e.getNewEndTime(),
				RequestStatus.valueOf(e.getUnformattedStatus()));
	}

	@Override
	public ChangeOfDateReq getById(Integer id) {
		return mapEntityToModel(ChangeOfDateReqCache.getInstance().getById(id));
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

		ChangeOfDateReq codr = getById(reqId);

		LectureService ls = new LectureService();
		ls.changeLectureDate(codr.getLecture().getId(), codr.getNewDow(),
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

		List<ChangeOfDateReqEntity> codrsEntity;

		codrsEntity = dao.getAllWaiting();

		List<String> codrInfo = new ArrayList<>();
		for (ChangeOfDateReqEntity codrEntity : codrsEntity) {

			ChangeOfDateReq codr = mapEntityToModel(codrEntity);
			codrInfo.add(codr.getId() + " Professore: " +
					codr.getAskingTeacher().getFname() + " " + codr.getAskingTeacher().getLname());
		}
		return codrInfo;
	}

	public String getCODROldTimeAndDate(int reqId) throws UnauthorizedException {
		int lectureId = getById(reqId).getLecture().getId();
		LectureService lService = new LectureService();
		Lecture lecture = lService.getById(lectureId);
		return lecture.getDayofweek().toString() + " " + lecture.getTimeInterval();
	}

	public String getCODRNewTimeAndDate(int reqId) throws UnauthorizedException {
		ChangeOfDateReq codr = getById(reqId);
		return codr.getNewDow().toString() + " " +
				codr.getNewStartTime().toString() + " - " + codr.getNewEndTime().toString();
	}
}
