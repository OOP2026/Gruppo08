package daoImplementation;

import java.sql.*;
import java.util.NoSuchElementException;

import daoImplementation.exception.DataInsertionException;
import daoImplementation.exception.DataRetrievalException;
import model.Classroom;

public class ClassroomPostgresDao extends AbstractSqldao<Classroom, String> {
	@Override
	public Classroom getById(String name) throws NoSuchElementException {
		final String sql = "SELECT name FROM classroom WHERE name = ?";

		try (Connection con = databaseConnection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Classroom(name);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException("DB exception during getByName", e);
		}

		throw new NoSuchElementException("Classroom with name " + name + " not found");
	}

	public Classroom insertClassroom(String name) throws SQLException {
		final String sql = "INSERT INTO classroom(name) VALUES (?)";

		try (Connection con = databaseConnection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DataInsertionException("Unexpected error during call of insertClassroom(" + name + ")", e);
		}

		return new Classroom(name);
	}
}
