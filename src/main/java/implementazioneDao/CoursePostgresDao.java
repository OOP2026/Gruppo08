package implementazioneDao;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.sql.*;
import implementazioneDao.entity.CourseEntity;
import implementazioneDao.exception.DataInsertionException;
import implementazioneDao.exception.DataRetrievalException;
import dao.CourseDao;

public class CoursePostgresDao implements CourseDao {
	private CourseEntity mapRsToCourse(ResultSet rs) throws SQLException {
		int courseId = rs.getInt("course_id");
		int teacherUid = rs.getInt("teacher_uid");
		String name = rs.getString("name");
		int cfu = rs.getInt("cfu");
		int academicYear = rs.getInt("academic_year");
		boolean isActive = rs.getBoolean("is_active");

		return new CourseEntity(courseId, teacherUid, name, cfu, academicYear, isActive);
	}

	@Override
	public CourseEntity getById(Integer courseId) throws NoSuchElementException {
		final String sql = "SELECT course_id, teacher_uid, name, cfu, academic_year, is_active FROM course WHERE course_id = ?";

		try (Connection con = database_connection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, courseId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRsToCourse(rs);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException("DB exception during getById", e);
		}

		throw new NoSuchElementException("Course with id " + courseId + " not found");
	}

	@Override
	public CourseEntity getByNameNYear(String name, int academicYear) {
		final String sql = "SELECT course_id, teacher_uid, name, cfu, academic_year, is_active FROM course WHERE name = ? AND academic_year = ?";

		try (Connection con = database_connection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, name);
			ps.setInt(2, academicYear);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRsToCourse(rs);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException("DB exception during getById", e);
		}

		throw new NoSuchElementException("Course with name " + name + academicYear + " not found");
	}

	@Override
	public void insertCourse(int teacherUid, String name, int cfu, int academicYear, boolean isActive) {
		final String sql = "INSERT INTO course(teacher_uid, name, cfu, academic_year, is_active) VALUES (?, ?, ?, ?, ?)";

		try (Connection con = database_connection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, teacherUid);
			ps.setString(2, name);
			ps.setInt(3, cfu);
			ps.setInt(4, academicYear);
			ps.setBoolean(5, isActive);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DataInsertionException("Unexpected error during course insertion", e);
		}

	}

	@Override
	public List<CourseEntity> getAllActiveCourses() {
		final String sql = "SELECT course_id, teacher_uid, name, cfu, academic_year, is_active FROM course WHERE is_active = true";

		List<CourseEntity> courses = new ArrayList<>();

		try (Connection con = database_connection.DbConnection.getCon();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql)) {

			while (rs.next()) {
				courses.add(mapRsToCourse(rs));
			}

		} catch (SQLException e) {
			throw new DataRetrievalException("Unexpected error during retrieval of all teachers", e);
		}

		return courses;
	}
}
