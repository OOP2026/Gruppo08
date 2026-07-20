package daoImplementation;

import java.sql.*;
import java.util.NoSuchElementException;

import model.Student;
import model.Teacher;
import model.User;

public class UserPostgresDao extends AbstractSqldao<User, Integer> {
	private User mapRowToUser(ResultSet rs) throws SQLException {
		int userId = rs.getInt("user_id");
		String fname = rs.getString("fname");
		String lname = rs.getString("lname");
		String email = rs.getString("email");
		String login = rs.getString("login");
		String password = rs.getString("password");

		// Controlla se l'utente ha i dati da studente
		int studentId = rs.getInt("student_id");
		if (!rs.wasNull()) {
			int academicYear = rs.getInt("academic_year");
			return new Student(studentId, academicYear, userId, fname, lname, email, login, password);
		}

		// analogo per teacher
		boolean isCoordinator = rs.getBoolean("is_coordinator");
		if (!rs.wasNull()) {
			return new Teacher(isCoordinator, userId, fname, lname, email, login, password);
		}

		throw new IllegalStateException("User " + userId + " is neither a student nor a teacher.");
	}

	@Override
	public User getById(Integer userId) {
		final String sql = "SELECT u.user_id, u.fname, u.lname, u.email, u.login, u.password, s.student_id, s.academic_year, t.is_coordinator FROM app_user u LEFT JOIN student s ON u.user_id = s.user_id LEFT JOIN teacher t ON u.user_id = t.user_id WHERE u.user_id = ?";

		Connection con = dbc.getCon();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToUser(rs);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("DB exception during getUserById", e);
		}

		throw new NoSuchElementException("User with ID " + userId + " not found");
	}

	public User getUserByLogin(String login) {
		final String sql = "SELECT u.user_id, u.fname, u.lname, u.email, u.login, u.password, s.student_id, s.academic_year, t.is_coordinator FROM app_user u LEFT JOIN student s ON u.user_id = s.user_id LEFT JOIN teacher t ON u.user_id = t.user_id WHERE u.login = ?";

		Connection con = dbc.getCon();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, login);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToUser(rs);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("DB exception during getUserByLogin", e);
		}

		throw new NoSuchElementException("User with login " + login + " not found");
	}

	public User getUserByEmail(String email) {
		final String sql = "SELECT u.user_id, u.fname, u.lname, u.email, u.login, u.password, s.student_id, s.academic_year, t.is_coordinator FROM app_user u LEFT JOIN student s ON u.user_id = s.user_id LEFT JOIN teacher t ON u.user_id = t.user_id WHERE u.email = ?";

		Connection con = dbc.getCon();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToUser(rs);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("DB exception during getUserByEmail", e);
		}

		throw new NoSuchElementException("User with email " + email + " not found");
	}

	public Student insertStudent(int academicYear, String fname, String lname, String email,
			String login, String password) throws SQLException {

		Connection con = dbc.getCon();

		// Uso RETURN_GENERATED_KEYS di Postgres per ottenere subito l'ID
		// senza dover fare una query di SELECT alla fine.
		final String usql = "INSERT INTO app_user(fname, lname, email, login, password) VALUES (?, ?, ?, ?, ?)";
		final String ssql = "INSERT INTO student(user_id, academic_year) VALUES (?, ?)";

		int newUserId = -1;
		int newStudentId = -1;

		try {
			con.setAutoCommit(false);

			try (PreparedStatement psUser = con.prepareStatement(usql, Statement.RETURN_GENERATED_KEYS)) {
				psUser.setString(1, fname);
				psUser.setString(2, lname);
				psUser.setString(3, email);
				psUser.setString(4, login);
				psUser.setString(5, password);
				psUser.executeUpdate();

				try (ResultSet rs = psUser.getGeneratedKeys()) {
					if (rs.next()) {
						// la prima colonna restituita è la chiave primaria
						newUserId = rs.getInt(1);
					} else {
						throw new SQLException("User insertion failed, no ID found.");
					}
				}
			}

			try (PreparedStatement psStudent = con.prepareStatement(ssql, Statement.RETURN_GENERATED_KEYS)) {
				psStudent.setInt(1, newUserId);
				psStudent.setInt(2, academicYear);
				psStudent.executeUpdate();

				try (ResultSet rs = psStudent.getGeneratedKeys()) {
					if (rs.next()) {
						newStudentId = rs.getInt(1);
					}
				}
			}

			con.commit();

			return new Student(newStudentId, academicYear, newUserId, fname, lname, email, login, password);

		} catch (SQLException e) {
			if (con != null) {
				con.rollback();
			}
			throw e;
		} finally {
			if (con != null) {
				con.setAutoCommit(true);
			}

			dbc.closeConnection();
		}
	}

	public Teacher insertTeacher(boolean isCoordinator, String fname, String lname, String email,
			String login, String password) throws SQLException {

		Connection con = dbc.getCon();

		// Uso RETURN_GENERATED_KEYS di Postgres per ottenere subito l'ID
		// senza dover fare una query di SELECT alla fine.
		final String usql = "INSERT INTO app_user(fname, lname, email, login, password) VALUES (?, ?, ?, ?, ?)";
		final String tsql = "INSERT INTO teacher(user_id, is_coordinator) VALUES (?, ?)";

		int newUserId = -1;

		try {
			con.setAutoCommit(false);

			try (PreparedStatement psUser = con.prepareStatement(usql, Statement.RETURN_GENERATED_KEYS)) {
				psUser.setString(1, fname);
				psUser.setString(2, lname);
				psUser.setString(3, email);
				psUser.setString(4, login);
				psUser.setString(5, password);
				psUser.executeUpdate();

				try (ResultSet rs = psUser.getGeneratedKeys()) {
					if (rs.next()) {
						// la prima colonna restituita è la chiave primaria
						newUserId = rs.getInt(1);
					} else {
						throw new SQLException("User insertion failed, no ID found.");
					}
				}
			}

			try (PreparedStatement psTeacher = con.prepareStatement(tsql, Statement.RETURN_GENERATED_KEYS)) {
				psTeacher.setInt(1, newUserId);
				psTeacher.setBoolean(2, isCoordinator);
				psTeacher.executeUpdate();
			}

			con.commit();

			return new Teacher(isCoordinator, newUserId, fname, lname, email, login, password);

		} catch (SQLException e) {
			if (con != null) {
				con.rollback();
			}
			throw e;
		} finally {
			if (con != null) {
				con.setAutoCommit(true);
			}

			dbc.closeConnection();
		}
	}
}
