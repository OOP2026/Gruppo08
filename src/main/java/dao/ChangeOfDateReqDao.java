package dao;

import model.ChangeOfDateReq;
import model.RequestStatus;

import java.util.List;
import java.util.NoSuchElementException;

import daoImplementation.ChangeOfDateReqPostgresDao;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

public class ChangeOfDateReqDao {
	private static ChangeOfDateReqDao instance;

	private List<ChangeOfDateReq> codreqInMem = new ArrayList<>();

	private ChangeOfDateReqDao() {
	}

	public static ChangeOfDateReqDao getInstance() {
		if (instance == null)
			instance = new ChangeOfDateReqDao();
		return instance;
	}

	public List<ChangeOfDateReq> getCodReqInMem() {
		return new ArrayList<>(codreqInMem);
	}

	private boolean isIdInMem(int reqId) {
		for (ChangeOfDateReq c : codreqInMem) {
			if (c.getReqId() == reqId)
				return true;
		}
		return false;
	}

	private ChangeOfDateReq getByIdInMem(int reqId) throws NoSuchElementException {
		for (ChangeOfDateReq c : codreqInMem) {
			if (c.getReqId() == reqId)
				return c;
		}

		throw new NoSuchElementException(reqId + " request id not found in mem");
	}

	public ChangeOfDateReq getById(int reqId) throws NoSuchElementException {
		if (isIdInMem(reqId)) {
			try {
				return getByIdInMem(reqId);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		ChangeOfDateReqPostgresDao psqldao = new ChangeOfDateReqPostgresDao();
		ChangeOfDateReq c = psqldao.getById(reqId);
		codreqInMem.add(c);
		return c;
	}

	public void insertCodReq(int askingTeacherUid, int lectureId, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime)
			throws SQLException {
		ChangeOfDateReqPostgresDao psqldao = new ChangeOfDateReqPostgresDao();
		ChangeOfDateReq c = psqldao.insertCodReq(askingTeacherUid, lectureId, newDow, newStartTime,
				newEndTime);
		codreqInMem.add(c);
	}

	public void changeStatusOfCODR(int reviewingCoordId, int reqId, boolean isApproved) throws NoSuchElementException {
		RequestStatus status = isApproved ? RequestStatus.APPROVED : RequestStatus.REJECTED;

		ChangeOfDateReq c = getById(reqId);

		c.setStatus(status);

		try {
			ChangeOfDateReqPostgresDao psqldao = new ChangeOfDateReqPostgresDao();
			psqldao.changeStatusOfCODR(reviewingCoordId, reqId, status);
		} catch (SQLException e) {
			throw new RuntimeException("unexpected error occured during call of method: changeStatusOfCODR");
		}
	}

}
