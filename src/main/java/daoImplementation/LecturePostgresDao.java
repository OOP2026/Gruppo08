package daoImplementation;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;
import dao.ClassroomDao;
import dao.CourseDao;
import daoImplementation.exception.DataInsertionException;
import daoImplementation.exception.DataRetrievalException;
import daoImplementation.exception.DataUpdateException;
import model.Lecture;
import model.Classroom;
import model.Course;

public class LecturePostgresDao extends AbstractSqldao<Lecture, Integer> {
	private Lecture mapRsToLecture(ResultSet rs) throws SQLException {
		int lectureId = rs.getInt("lecture_id");
		int courseId = rs.getInt("course_id");
		Course course = CourseDao.getInstance().getById(courseId);
		String classroomName = rs.getString("classroom_name");
		Classroom classroom = ClassroomDao.getInstance().getById(classroomName);
		String unformattedDow = rs.getString("dayofweek");
		DayOfWeek dayofweek = DayOfWeek.valueOf(unformattedDow);
		LocalTime startTime = rs.getObject("start_time", LocalTime.class);
		LocalTime endTime = rs.getObject("end_time", LocalTime.class);

		return new Lecture(lectureId, course, classroom, dayofweek, startTime, endTime);
	}

	@Override
	public Lecture getById(Integer lectureId) throws NoSuchElementException {
		final String sql = "SELECT * FROM lecture WHERE lecture_id = ?";

		try (Connection con = databaseConnection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, lectureId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRsToLecture(rs);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("DB exception during getById", e);
		}

		throw new NoSuchElementException("Lecture with id " + lectureId + " not found");
	}

	public List<Lecture> getAllByAcademicYear(int academicYear) {
		final String sql = "SELECT lecture_id, l.course_id, classroom_name, dayofweek, start_time, end_time FROM lecture l JOIN course c ON c.course_id = l.course_id WHERE c.academic_year = ?;";

		List<Lecture> lectures = new ArrayList<>();

		try (Connection con = databaseConnection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, academicYear);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Lecture l = mapRsToLecture(rs);
					lectures.add(l);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException(
					"Unexpected error during call of getAllByAcademicYear(" + academicYear + ")", e);
		}

		return lectures;
	}

	public List<Lecture> getAllByTeacher(int teacherUid) throws SQLException {
		final String sql = "SELECT lecture_id, l.course_id, classroom_name, dayofweek, start_time, end_time FROM lecture l JOIN course c ON l.course_id = c.course_id WHERE teacher_uid = ?";

		List<Lecture> lectures = new ArrayList<>();

		try (Connection con = databaseConnection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, teacherUid);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Lecture l = mapRsToLecture(rs);
					lectures.add(l);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException(
					"Unexpected error during call of getAllByTeacher(" + teacherUid + ")", e);
		}

		return lectures;

	}

	public Lecture insertLecture(int courseId, String classroomName, DayOfWeek dayofweek, LocalTime startTime,
			LocalTime endTime) {
		final String sql = "INSERT INTO lecture(course_id, classroom_name, dayofweek, start_time, end_time) VALUES (?, ?, ?::dow, ?::time, ?::time)";

		int newLectureId = -1;

		try (Connection con = databaseConnection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, courseId);
			ps.setString(2, classroomName);
			ps.setString(3, dayofweek.toString());
			ps.setObject(4, startTime);
			ps.setObject(5, endTime);
			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					// la prima colonna restituita è la chiave primaria
					newLectureId = rs.getInt(1);
				} else {
					throw new DataInsertionException("Course insertion failed, no ID found.");
				}
			}

		} catch (SQLException e) {
			throw new DataInsertionException("Unexpected error during insertion of lecture", e);
		}

		return new Lecture(newLectureId, CourseDao.getInstance().getById(courseId),
				ClassroomDao.getInstance().getById(classroomName), dayofweek, startTime, endTime);
	}

	public void updateLectureDate(int lectureId, DayOfWeek newDow, LocalTime newStartTime, LocalTime newEndTime) {
		final String sql = "UPDATE lecture SET dayofweek = ?::dow, start_time = ?::time, end_time = ?::time WHERE lecture_id = ?";

		try (Connection con = databaseConnection.DbConnection.getCon();
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
