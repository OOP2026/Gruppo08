package daoImplementation;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.NoSuchElementException;

import model.ChangeOfDateReq;
import model.Lecture;
import model.RequestStatus;
import databaseConnection.DbConnection;
import model.Teacher;
import dao.LectureDao;
import dao.UserDao;

public class ChangeOfDateReqPostgresDao {
	private DbConnection dbc = DbConnection.getInstance();

	private ChangeOfDateReq mapRsToCodReq(ResultSet rs) throws SQLException {
		int reqId = rs.getInt("req_id");
		int askingTeacherUid = rs.getInt("asking_teacher_id");
		Teacher askingTeacher = (Teacher) UserDao.getInstance().getUserById(askingTeacherUid);
		int reviewingCoordUid = rs.getInt("reviewing_coord_id");
		Teacher reviewingCoord = (Teacher) UserDao.getInstance().getUserById(reviewingCoordUid);
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

	public ChangeOfDateReq getById(int reqId) throws NoSuchElementException {
		final String sql = "SELECT * FROM change_of_date_req WHERE req_id = ?";

		Connection con = dbc.getCon();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, reqId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRsToCodReq(rs);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("DB exception during getById", e);
		} finally {
			dbc.closeConnection();
		}

		throw new NoSuchElementException("ChangeOfDateReq with id " + reqId + " not found");
	}

	public ChangeOfDateReq insertCodReq(int askingTeacherUid, int lectureId, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime)
			throws SQLException {

		final String sql = "INSERT INTO change_of_date_req(asking_teacher_id, lecture_id, new_dayofweek, new_start_time, new_end_time) VALUES (?, ?, ?::dow, ?::time, ?::time)";

		int newReqId = -1;
		RequestStatus newStatus = RequestStatus.WAITING;
		Teacher newReviewingCoord = null;

		Connection con = dbc.getCon();
		try (PreparedStatement ps = con.prepareStatement(sql,
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
					throw new SQLException("Course insertion failed, no ID found.");
				}
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			dbc.closeConnection();
		}

		return new ChangeOfDateReq(newReqId, (Teacher) UserDao.getInstance().getUserById(askingTeacherUid),
				newReviewingCoord,
				LectureDao.getInstance().getById(lectureId), newDow, newStartTime, newEndTime, newStatus);
	}

}
