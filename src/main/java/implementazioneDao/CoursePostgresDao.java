package implementazioneDao;

import java.util.NoSuchElementException;
import java.sql.*;
import dao.dto.CourseDTO;
import implementazioneDao.exception.DataInsertionException;
import implementazioneDao.exception.DataRetrievalException;
import dao.CourseDao;

public class CoursePostgresDao implements CourseDao {
	private CourseDTO mapRsToCourse(ResultSet rs) throws SQLException {
		int courseId = rs.getInt("course_id");
		int teacherUid = rs.getInt("teacher_uid");
		String name = rs.getString("name");
		int cfu = rs.getInt("cfu");
		int academicYear = rs.getInt("academic_year");
		boolean isActive = rs.getBoolean("is_active");

		return new CourseDTO(courseId, teacherUid, name, cfu, academicYear, isActive);
	}

	@Override
	public CourseDTO getById(Integer courseId) throws NoSuchElementException {
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
	public CourseDTO getByNameNYear(String name, int academicYear) {
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
}
