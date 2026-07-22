package dao;

import dao.impl.exception.DataUpdateException;
import model.ChangeOfDateReq;
import model.RequestStatus;
import java.util.List;
import controller.exception.DatabaseException;
import dao.impl.ChangeOfDateReqPostgresDao;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class ChangeOfDateReqDao extends AbstractDao<ChangeOfDateReq, ChangeOfDateReqPostgresDao, Integer> {
	private static ChangeOfDateReqDao instance;

	private ChangeOfDateReqDao() {
		super(new ChangeOfDateReqPostgresDao());
	}

	public static ChangeOfDateReqDao getInstance() {
		if (instance == null)
			instance = new ChangeOfDateReqDao();
		return instance;
	}

	/**
	 * @return lista di tutte le richieste in attesa
	 */
	public List<ChangeOfDateReq> getAllWaiting() {
		List<ChangeOfDateReq> codrs = sqldao.getAllWaiting();

		for (ChangeOfDateReq c : codrs) {
			inMem.add(c);
		}

		return codrs;
	}

	/**
	 * Inserisce una ChangeOfDateReq in memory e nel db
	 * 
	 * @throws dao.impl.exception.DataInsertionException
	 */
	public void insertCodReq(int askingTeacherUid, int lectureId, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime) {
		ChangeOfDateReq c = sqldao.insertCodReq(askingTeacherUid, lectureId, newDow, newStartTime,
				newEndTime);
		inMem.add(c);
	}

	/**
	 * 
	 * @param reviewingCoordId id del coordinatore che cambia lo stato della
	 *                         richiesta
	 * @param reqId            id della richiesta da modificare
	 * @param isApproved       se true RequestStatus.APPROVED altrimenti
	 *                         RequestStatus.REJECTED.
	 * @throws DatabaseException
	 */
	public void changeStatusOfCODR(int reviewingCoordId, int reqId, boolean isApproved) {
		RequestStatus status = isApproved ? RequestStatus.APPROVED : RequestStatus.REJECTED;

		ChangeOfDateReq c = getById(reqId);

		c.setStatus(status);

		try {
			sqldao.changeStatusOfCODR(reviewingCoordId, reqId, status);
		} catch (DataUpdateException e) {
			throw new DatabaseException("unexpected error occured during call of method: changeStatusOfCODR", e);
		}
	}

}
