package dao;

import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalTime;
import implementazioneDao.entity.ChangeOfDateReqEntity;

public interface ChangeOfDateReqDao extends GenericDao<ChangeOfDateReqEntity, Integer> {
	/**
	 * @return lista di tutte le richieste in attesa
	 */
	public List<ChangeOfDateReqEntity> getAllWaiting();

	/**
	 * @throws implementazioneDao.exception.DataInsertionException
	 */
	public void insertCodReq(int askingTeacherUid, int lectureId, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime);

	/**
	 * 
	 * @param reviewingCoordId id del coordinatore che cambia lo stato della
	 *                         richiesta
	 * @param reqId            id della richiesta da modificare
	 * @param isApproved       se true RequestStatus.APPROVED altrimenti
	 *                         RequestStatus.REJECTED.
	 * @throws controller.exception.DatabaseException
	 */
	public void changeStatusOfCODR(int reviewingCoordId, int reqId, boolean isApproved);

}
