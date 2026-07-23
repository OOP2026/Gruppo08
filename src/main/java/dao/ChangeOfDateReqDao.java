package dao;

import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalTime;
import dao.dto.ChangeOfDateReqDTO;

public interface ChangeOfDateReqDao extends GenericDao<ChangeOfDateReqDTO, Integer> {
	/**
	 * @return lista di tutte le richieste in attesa
	 */
	public List<ChangeOfDateReqDTO> getAllWaiting();

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
	 * @throws DatabaseException
	 */
	public void changeStatusOfCODR(int reviewingCoordId, int reqId, boolean isApproved);

}
