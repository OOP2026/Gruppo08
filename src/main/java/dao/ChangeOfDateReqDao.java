package dao;

import model.ChangeOfDateReq;
import model.RequestStatus;

import java.util.List;
import java.util.NoSuchElementException;

import controller.exception.DatabaseException;
import daoImplementation.ChangeOfDateReqPostgresDao;

import java.sql.SQLException;
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
	 * @throws daoImplementation.exception.DataInsertionException
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
	 * @throws NoSuchElementException
	 */
	public void changeStatusOfCODR(int reviewingCoordId, int reqId, boolean isApproved) throws NoSuchElementException {
		RequestStatus status = isApproved ? RequestStatus.APPROVED : RequestStatus.REJECTED;

		ChangeOfDateReq c = getById(reqId);

		c.setStatus(status);

		try {
			sqldao.changeStatusOfCODR(reviewingCoordId, reqId, status);
		} catch (SQLException e) {
			throw new DatabaseException("unexpected error occured during call of method: changeStatusOfCODR", e);
		}
	}

}
