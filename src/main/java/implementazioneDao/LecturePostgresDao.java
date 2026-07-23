package implementazioneDao;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;
import dao.LectureDao;
import implementazioneDao.entity.LectureEntity;
import implementazioneDao.exception.DataInsertionException;
import implementazioneDao.exception.DataRetrievalException;
import implementazioneDao.exception.DataUpdateException;

public class LecturePostgresDao implements LectureDao {
	private LectureEntity mapRsToLecture(ResultSet rs) throws SQLException {
		int lectureId = rs.getInt("lecture_id");
		int courseId = rs.getInt("course_id");
		String classroomName = rs.getString("classroom_name");
		String unformattedDow = rs.getString("dayofweek");
		DayOfWeek dayofweek = DayOfWeek.valueOf(unformattedDow);
		LocalTime startTime = rs.getObject("start_time", LocalTime.class);
		LocalTime endTime = rs.getObject("end_time", LocalTime.class);

		return new LectureEntity(lectureId, courseId, classroomName, dayofweek, startTime, endTime);
	}

	@Override
	public LectureEntity getById(Integer lectureId) throws NoSuchElementException {
		final String sql = "SELECT lecture_id, course_id, classroom_name, dayofweek, start_time, end_time FROM lecture WHERE lecture_id = ?";

		try (Connection con = database_connection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, lectureId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRsToLecture(rs);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException("DB exception during getById", e);
		}

		throw new NoSuchElementException("Lecture with id " + lectureId + " not found");
	}

	@Override
	public List<LectureEntity> getAllByAcademicYear(int academicYear) {
		final String sql = "SELECT lecture_id, l.course_id, classroom_name, dayofweek, start_time, end_time FROM lecture l JOIN course c ON c.course_id = l.course_id WHERE c.academic_year = ?;";

		List<LectureEntity> lectures = new ArrayList<>();

		try (Connection con = database_connection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, academicYear);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					LectureEntity l = mapRsToLecture(rs);
					lectures.add(l);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException(
					"Unexpected error during call of getAllByAcademicYear(" + academicYear + ")", e);
		}

		return lectures;
	}

	@Override
	public List<LectureEntity> getAllByTeacher(int teacherUid) {
		final String sql = "SELECT lecture_id, l.course_id, classroom_name, dayofweek, start_time, end_time FROM lecture l JOIN course c ON l.course_id = c.course_id WHERE teacher_uid = ?";

		List<LectureEntity> lectures = new ArrayList<>();

		try (Connection con = database_connection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, teacherUid);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					LectureEntity l = mapRsToLecture(rs);
					lectures.add(l);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException(
					"Unexpected error during call of getAllByTeacher(" + teacherUid + ")", e);
		}

		return lectures;

	}

	@Override
	public void insertLecture(int courseId, String classroomName, DayOfWeek dayofweek, LocalTime startTime,
			LocalTime endTime) {
		final String sql = "INSERT INTO lecture(course_id, classroom_name, dayofweek, start_time, end_time) VALUES (?, ?, ?::dow, ?::time, ?::time)";

		try (Connection con = database_connection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, courseId);
			ps.setString(2, classroomName);
			ps.setString(3, dayofweek.toString());
			ps.setObject(4, startTime);
			ps.setObject(5, endTime);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DataInsertionException("Unexpected error during insertion of lecture", e);
		}

	}

	@Override
	public void changeLectureDate(int lectureId, DayOfWeek newDow, LocalTime newStartTime, LocalTime newEndTime) {
		final String sql = "UPDATE lecture SET dayofweek = ?::dow, start_time = ?::time, end_time = ?::time WHERE lecture_id = ?";

		try (Connection con = database_connection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, newDow.name());
			ps.setObject(2, newStartTime);
			ps.setObject(3, newEndTime);
			ps.setInt(4, lectureId);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DataUpdateException("Unexpected error during update of lecture date", e);
		}
	}
}
