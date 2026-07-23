package implementazioneDao;

import java.sql.*;
import java.util.NoSuchElementException;
import implementazioneDao.exception.DataInsertionException;
import implementazioneDao.exception.DataRetrievalException;
import dao.dto.ClassroomDTO;
import dao.ClassroomDao;

public class ClassroomPostgresDao implements ClassroomDao {
	@Override
	public ClassroomDTO getById(String name) throws NoSuchElementException {
		final String sql = "SELECT name FROM classroom WHERE name = ?";

		try (Connection con = database_connection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new ClassroomDTO(name);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException("DB exception during getByName", e);
		}

		throw new NoSuchElementException("Classroom with name " + name + " not found");
	}

	@Override
	public void insertClassroom(String name) {
		final String sql = "INSERT INTO classroom(name) VALUES (?)";

		try (Connection con = database_connection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DataInsertionException("Unexpected error during call of insertClassroom(" + name + ")", e);
		}
	}
}
