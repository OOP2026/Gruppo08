package controller;

import java.sql.SQLException;
import java.time.LocalTime;
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

	// TODO: Metodi per cambiare lo stato di una ChangeOfDateReq
}
