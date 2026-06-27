package daoImplementation;

import model.Course;
import java.util.NoSuchElementException;
import java.sql.*;
import databaseConnection.DbConnection;

import dao.UserDao;
import model.Teacher;

public class CoursePostgresDao {
	private DbConnection dbc = DbConnection.getInstance();

	private Course mapRsToCourse(ResultSet rs) throws SQLException {
		int courseId = rs.getInt("course_id");
		int teacherUid = rs.getInt("teacher_uid");
		Teacher teacher = (Teacher) UserDao.getInstance().getUserById(teacherUid);
		String name = rs.getString("name");
		int cfu = rs.getInt("cfu");
		int academicYear = rs.getInt("academic_year");
		boolean isActive = rs.getBoolean("is_active");

		return new Course(courseId, teacher, name, cfu, academicYear, isActive);
	}

	public Course getById(int courseId) throws NoSuchElementException {
		final String sql = "SELECT * FROM course WHERE course_id = ?";

		Connection con = dbc.getCon();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, courseId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRsToCourse(rs);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("DB exception during getById", e);
		} finally {
			dbc.closeConnection();
		}

		throw new NoSuchElementException("Course with id " + courseId + " not found");
	}

	public Course getByNameNYear(String name, int academicYear) {
		final String sql = "SELECT * FROM course WHERE name = ? AND academic_year = ?";

		Connection con = dbc.getCon();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, name);
			ps.setInt(2, academicYear);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRsToCourse(rs);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("DB exception during getById", e);
		} finally {
			dbc.closeConnection();
		}

		throw new NoSuchElementException("Course with name " + name + academicYear + " not found");
	}

	public Course insertCourse(int teacherUid, String name, int cfu, int academicYear, boolean isActive)
			throws SQLException {
		final String sql = "INSERT INTO course(teacher_uid, name, cfu, academic_year, is_active) VALUES (?, ?, ?, ?, ?)";

		int newCourseId = -1;

		Connection con = dbc.getCon();
		try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, teacherUid);
			ps.setString(2, name);
			ps.setInt(3, cfu);
			ps.setInt(4, academicYear);
			ps.setBoolean(5, isActive);
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) {
					// la prima colonna restituita è la chiave primaria
					newCourseId = rs.getInt(1);
				} else {
					throw new SQLException("Course insertion failed, no ID found.");
				}
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			dbc.closeConnection();
		}

		return new Course(newCourseId,
				(Teacher) UserDao.getInstance().getUserById(teacherUid),
				name, cfu, academicYear, isActive);

	}
}
