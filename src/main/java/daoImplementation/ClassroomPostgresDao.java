package daoImplementation;

import java.sql.*;
import java.util.NoSuchElementException;

import databaseConnection.DbConnection;
import model.Classroom;

public class ClassroomPostgresDao {
	private DbConnection dbc = DbConnection.getInstance();

	public Classroom getByName(String name) throws NoSuchElementException {
		final String sql = "SELECT name FROM classroom WHERE name = ?";

		Connection con = dbc.getCon();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Classroom(name);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("DB exception during getByName", e);
		} finally {
			dbc.closeConnection();
		}

		throw new NoSuchElementException("Classroom with name " + name + " not found");
	}

	public Classroom insertClassroom(String name) throws SQLException {
		final String sql = "INSERT INTO classroom(name) VALUES (?)";

		Connection con = dbc.getCon();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			dbc.closeConnection();
		}

		return new Classroom(name);

	}
}
