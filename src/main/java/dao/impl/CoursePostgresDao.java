package dao.impl;

import model.Course;
import java.util.NoSuchElementException;
import java.sql.*;

import dao.UserDao;
import dao.impl.exception.DataInsertionException;
import dao.impl.exception.DataRetrievalException;
import model.Teacher;

public class CoursePostgresDao extends AbstractSqldao<Course, Integer> {
	private Course mapRsToCourse(ResultSet rs) throws SQLException {
		int courseId = rs.getInt("course_id");
		int teacherUid = rs.getInt("teacher_uid");
		Teacher teacher = (Teacher) UserDao.getInstance().getById(teacherUid);
		String name = rs.getString("name");
		int cfu = rs.getInt("cfu");
		int academicYear = rs.getInt("academic_year");
		boolean isActive = rs.getBoolean("is_active");

		return new Course(courseId, teacher, name, cfu, academicYear, isActive);
	}

	@Override
	public Course getById(Integer courseId) throws NoSuchElementException {
		final String sql = "SELECT course_id, teacher_uid, name, cfu, academic_year, is_active FROM course WHERE course_id = ?";

		try (Connection con = dbconnection.DbConnection.getCon();
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

	public Course getByNameNYear(String name, int academicYear) {
		final String sql = "SELECT course_id, teacher_uid, name, cfu, academic_year, is_active FROM course WHERE name = ? AND academic_year = ?";

		try (Connection con = dbconnection.DbConnection.getCon();
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

	public Course insertCourse(int teacherUid, String name, int cfu, int academicYear, boolean isActive) {
		final String sql = "INSERT INTO course(teacher_uid, name, cfu, academic_year, is_active) VALUES (?, ?, ?, ?, ?)";

		int newCourseId = -1;

		try (Connection con = dbconnection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
					throw new DataInsertionException("Course insertion failed, no ID found.");
				}
			}
		} catch (SQLException e) {
			throw new DataInsertionException("Unexpected error during course insertion", e);
		}

		return new Course(newCourseId,
				(Teacher) UserDao.getInstance().getById(teacherUid),
				name, cfu, academicYear, isActive);

	}
}
