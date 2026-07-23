package implementazioneDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import dao.UserDao;
import implementazioneDao.entity.StudentEntity;
import implementazioneDao.entity.TeacherEntity;
import implementazioneDao.entity.UserEntity;
import implementazioneDao.exception.DataInsertionException;
import implementazioneDao.exception.DataRetrievalException;

public class UserPostgresDao implements UserDao {

	private UserEntity mapRowToUser(ResultSet rs) throws SQLException {
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
			return new StudentEntity(userId, fname, lname, email, login, password, studentId, academicYear);
		}

		// Controlla se l'utente ha i dati da docente
		boolean isCoordinator = rs.getBoolean("is_coordinator");
		if (!rs.wasNull()) {
			return new TeacherEntity(userId, fname, lname, email, login, password, isCoordinator);
		}

		throw new IllegalStateException("User " + userId + " is neither a student nor a teacher.");
	}

	@Override
	public UserEntity getById(Integer userId) {
		final String sql = "SELECT u.user_id, u.fname, u.lname, u.email, u.login, u.password, " +
				"s.student_id, s.academic_year, t.is_coordinator " +
				"FROM app_user u " +
				"LEFT JOIN student s ON u.user_id = s.user_id " +
				"LEFT JOIN teacher t ON u.user_id = t.user_id " +
				"WHERE u.user_id = ?";

		try (Connection con = database_connection.DbConnection.getCon();
		     PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToUser(rs);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException("DB exception during getUserById", e);
		}

		throw new NoSuchElementException("User with ID " + userId + " not found");
	}

	@Override
	public UserEntity getUserByLogin(String login) {
		final String sql = "SELECT u.user_id, u.fname, u.lname, u.email, u.login, u.password, " +
				"s.student_id, s.academic_year, t.is_coordinator " +
				"FROM app_user u " +
				"LEFT JOIN student s ON u.user_id = s.user_id " +
				"LEFT JOIN teacher t ON u.user_id = t.user_id " +
				"WHERE u.login = ?";

		try (Connection con = database_connection.DbConnection.getCon();
		     PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, login);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToUser(rs);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException("DB exception during getUserByLogin", e);
		}

		throw new NoSuchElementException("User with login " + login + " not found");
	}

	@Override
	public UserEntity getUserByEmail(String email) {
		final String sql = "SELECT u.user_id, u.fname, u.lname, u.email, u.login, u.password, " +
				"s.student_id, s.academic_year, t.is_coordinator " +
				"FROM app_user u " +
				"LEFT JOIN student s ON u.user_id = s.user_id " +
				"LEFT JOIN teacher t ON u.user_id = t.user_id " +
				"WHERE u.email = ?";

		try (Connection con = database_connection.DbConnection.getCon();
		     PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, email);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRowToUser(rs);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException("DB exception during getUserByEmail", e);
		}

		throw new NoSuchElementException("User with email " + email + " not found");
	}

	@Override
	public void insertStudent(int academicYear, String fname, String lname, String email,
	                          String login, String password) throws SQLException {

		Connection con;
		try {
			con = database_connection.DbConnection.getCon();
		} catch (SQLException e) {
			throw new DataInsertionException("Unable to establish connection", e);
		}

		final String usql = "INSERT INTO app_user(fname, lname, email, login, password) VALUES (?, ?, ?, ?, ?)";
		final String ssql = "INSERT INTO student(user_id, academic_year) VALUES (?, ?)";

		try {
			con.setAutoCommit(false);
			int newUserId;

			try (PreparedStatement psUser = con.prepareStatement(usql, Statement.RETURN_GENERATED_KEYS)) {
				psUser.setString(1, fname);
				psUser.setString(2, lname);
				psUser.setString(3, email);
				psUser.setString(4, login);
				psUser.setString(5, password);
				psUser.executeUpdate();

				try (ResultSet rs = psUser.getGeneratedKeys()) {
					if (rs.next()) {
						newUserId = rs.getInt(1);
					} else {
						throw new SQLException("Inserimento utente fallito, nessun ID generato.");
					}
				}
			}

			try (PreparedStatement psStudent = con.prepareStatement(ssql)) {
				psStudent.setInt(1, newUserId);
				psStudent.setInt(2, academicYear);
				psStudent.executeUpdate();
			}

			con.commit();

		} catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			con.setAutoCommit(true);
			con.close();
		}
	}

	@Override
	public void insertTeacher(boolean isCoordinator, String fname, String lname, String email,
	                          String login, String password) throws SQLException {

		Connection con;
		try {
			con = database_connection.DbConnection.getCon();
		} catch (SQLException e) {
			throw new DataInsertionException("Unable to establish connection", e);
		}

		final String usql = "INSERT INTO app_user(fname, lname, email, login, password) VALUES (?, ?, ?, ?, ?)";
		final String tsql = "INSERT INTO teacher(user_id, is_coordinator) VALUES (?, ?)";

		try {
			con.setAutoCommit(false);
			int newUserId;

			try (PreparedStatement psUser = con.prepareStatement(usql, Statement.RETURN_GENERATED_KEYS)) {
				psUser.setString(1, fname);
				psUser.setString(2, lname);
				psUser.setString(3, email);
				psUser.setString(4, login);
				psUser.setString(5, password);
				psUser.executeUpdate();

				try (ResultSet rs = psUser.getGeneratedKeys()) {
					if (rs.next()) {
						newUserId = rs.getInt(1);
					} else {
						throw new SQLException("Inserimento utente fallito, nessun ID generato.");
					}
				}
			}

			try (PreparedStatement psTeacher = con.prepareStatement(tsql)) {
				psTeacher.setInt(1, newUserId);
				psTeacher.setBoolean(2, isCoordinator);
				psTeacher.executeUpdate();
			}

			con.commit();

		} catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			con.setAutoCommit(true);
			con.close();
		}
	}

	@Override
	public List<TeacherEntity> getAllTeachers() {
		final String sql = "SELECT u.user_id, u.fname, u.lname, u.email, u.login, u.password, t.is_coordinator " +
				"FROM teacher t INNER JOIN app_user u ON t.user_id = u.user_id";

		List<TeacherEntity> teachers = new ArrayList<>();

		try (Connection con = database_connection.DbConnection.getCon();
		     Statement s = con.createStatement();
		     ResultSet rs = s.executeQuery(sql)) {

			while (rs.next()) {
				teachers.add(new TeacherEntity(
						rs.getInt("user_id"),
						rs.getString("fname"),
						rs.getString("lname"),
						rs.getString("email"),
						rs.getString("login"),
						rs.getString("password"),
						rs.getBoolean("is_coordinator")
				));
			}

		} catch (SQLException e) {
			throw new DataRetrievalException("Unexpected error during retrieval of all teachers", e);
		}

		return teachers;
	}
}