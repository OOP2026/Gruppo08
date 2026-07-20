package daoImplementation;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import model.ChangeOfDateReq;
import model.Lecture;
import model.RequestStatus;
import model.Teacher;
import dao.LectureDao;
import dao.UserDao;
import daoImplementation.exception.DataInsertionException;
import daoImplementation.exception.DataRetrievalException;
import daoImplementation.exception.DataUpdateException;

public class ChangeOfDateReqPostgresDao extends AbstractSqldao<ChangeOfDateReq, Integer> {
	private ChangeOfDateReq mapRsToCodReq(ResultSet rs) throws SQLException {
		int reqId = rs.getInt("req_id");
		int askingTeacherUid = rs.getInt("asking_teacher_id");
		Teacher askingTeacher = (Teacher) UserDao.getInstance().getById(askingTeacherUid);
		int reviewingCoordUid = rs.getInt("reviewing_coord_id");
		Teacher reviewingCoord = null;
		if (!rs.wasNull())
			reviewingCoord = (Teacher) UserDao.getInstance().getById(reviewingCoordUid);
		int lectureId = rs.getInt("lecture_id");
		Lecture lecture = LectureDao.getInstance().getById(lectureId);
		String unformattedDow = rs.getString("new_dayofweek");
		DayOfWeek newDow = DayOfWeek.valueOf(unformattedDow);
		LocalTime newStartTime = rs.getObject("new_start_time", LocalTime.class);
		LocalTime newEndTime = rs.getObject("new_end_time", LocalTime.class);
		String unformattedStatus = rs.getString("status");
		RequestStatus status = RequestStatus.valueOf(unformattedStatus);

		return new ChangeOfDateReq(reqId, askingTeacher, reviewingCoord, lecture, newDow, newStartTime, newEndTime,
				status);
	}

	@Override
	public ChangeOfDateReq getById(Integer reqId) throws NoSuchElementException {
		final String sql = "SELECT req_id, asking_teacher_id, reviewing_coord_id, lecture_id, new_dayofweek, new_start_time, new_end_time, status FROM change_of_date_req WHERE req_id = ?";

		try (Connection con = databaseConnection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, reqId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRsToCodReq(rs);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException("DB exception during getById", e);
		}

		throw new NoSuchElementException("ChangeOfDateReq with id " + reqId + " not found");
	}

	public List<ChangeOfDateReq> getAllWaiting() throws DataRetrievalException {
		final String sql = "SELECT req_id, asking_teacher_id, reviewing_coord_id, lecture_id, new_dayofweek, new_start_time, new_end_time, status FROM change_of_date_req WHERE status = 'WAITING'";

		List<ChangeOfDateReq> codrs = new ArrayList<>();

		try (Connection con = databaseConnection.DbConnection.getCon();
				Statement s = con.createStatement()) {

			try (ResultSet rs = s.executeQuery(sql)) {
				while (rs.next()) {
					ChangeOfDateReq codr = mapRsToCodReq(rs);
					codrs.add(codr);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException("Unexpected error during retrieval of CODR's with status = 'WAITING'", e);
		}

		return codrs;
	}

	public ChangeOfDateReq insertCodReq(int askingTeacherUid, int lectureId, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime) {

		final String sql = "INSERT INTO change_of_date_req(asking_teacher_id, lecture_id, new_dayofweek, new_start_time, new_end_time) VALUES (?, ?, ?::dow, ?::time, ?::time)";

		int newReqId = -1;
		RequestStatus newStatus = RequestStatus.WAITING;
		Teacher newReviewingCoord = null;

		try (Connection con = databaseConnection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, askingTeacherUid);
			ps.setInt(2, lectureId);
			ps.setString(3, newDow.toString());
			ps.setObject(4, newStartTime);
			ps.setObject(5, newEndTime);
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					// la prima colonna restituita è la chiave primaria
					newReqId = rs.getInt(1);
				} else {
					throw new DataInsertionException("Course insertion failed, no ID found.");
				}
			}
		} catch (SQLException e) {
			throw new DataInsertionException("Unexpected exception during PreparedStatement execution", e);
		}

		return new ChangeOfDateReq(newReqId, (Teacher) UserDao.getInstance().getById(askingTeacherUid),
				newReviewingCoord,
				LectureDao.getInstance().getById(lectureId), newDow, newStartTime, newEndTime, newStatus);
	}

	public void changeStatusOfCODR(int reviewingCoordId, int reqId, RequestStatus status)
			throws SQLException {
		final String sql = "UPDATE change_of_date_req SET reviewing_coord_id = ?, status = ?::request_status WHERE req_id = ?;";

		try (Connection con = databaseConnection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, reviewingCoordId);
			ps.setString(2, status.name());
			ps.setInt(3, reqId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DataUpdateException("Unexpected error during call of method changeStatusOfCODR", e);
		}

	}

}
