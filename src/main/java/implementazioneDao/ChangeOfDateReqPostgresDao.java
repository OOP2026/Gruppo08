package implementazioneDao;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import implementazioneDao.entity.ChangeOfDateReqEntity;
import implementazioneDao.exception.DataInsertionException;
import implementazioneDao.exception.DataRetrievalException;
import implementazioneDao.exception.DataUpdateException;
import dao.ChangeOfDateReqDao;

public class ChangeOfDateReqPostgresDao implements ChangeOfDateReqDao {
	private ChangeOfDateReqEntity mapRsToCodReq(ResultSet rs) throws SQLException {
		int reqId = rs.getInt("req_id");
		int askingTeacherUid = rs.getInt("asking_teacher_id");
		Integer reviewingCoordUid = rs.getInt("reviewing_coord_id");
		if (rs.wasNull())
			reviewingCoordUid = null;
		int lectureId = rs.getInt("lecture_id");
		String unformattedDow = rs.getString("new_dayofweek");
		DayOfWeek newDow = DayOfWeek.valueOf(unformattedDow);
		LocalTime newStartTime = rs.getObject("new_start_time", LocalTime.class);
		LocalTime newEndTime = rs.getObject("new_end_time", LocalTime.class);
		String unformattedStatus = rs.getString("status");

		return new ChangeOfDateReqEntity(reqId, askingTeacherUid, reviewingCoordUid, lectureId, newDow, newStartTime,
				newEndTime, unformattedStatus);
	}

	@Override
	public ChangeOfDateReqEntity getById(Integer reqId) throws NoSuchElementException {
		final String sql = "SELECT req_id, asking_teacher_id, reviewing_coord_id, lecture_id, new_dayofweek, new_start_time, new_end_time, status FROM change_of_date_req WHERE req_id = ?";

		try (Connection con = database_connection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, reqId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRsToCodReq(rs);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException("DB exception during getById", e);
		}

		throw new NoSuchElementException("ChangeOfDateReq with id " + reqId + " not found");
	}

	@Override
	public List<ChangeOfDateReqEntity> getAllWaiting() throws DataRetrievalException {
		final String sql = "SELECT req_id, asking_teacher_id, reviewing_coord_id, lecture_id, new_dayofweek, new_start_time, new_end_time, status FROM change_of_date_req WHERE status = 'WAITING'";

		List<ChangeOfDateReqEntity> codrs = new ArrayList<>();

		try (Connection con = database_connection.DbConnection.getCon();
				Statement s = con.createStatement()) {

			try (ResultSet rs = s.executeQuery(sql)) {
				while (rs.next()) {
					ChangeOfDateReqEntity codr = mapRsToCodReq(rs);
					codrs.add(codr);
				}
			}

		} catch (SQLException e) {
			throw new DataRetrievalException("Unexpected error during retrieval of CODR's with status = 'WAITING'", e);
		}

		return codrs;
	}

	@Override
	public void insertCodReq(int askingTeacherUid, int lectureId, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime) {

		final String sql = "INSERT INTO change_of_date_req(asking_teacher_id, lecture_id, new_dayofweek, new_start_time, new_end_time) VALUES (?, ?, ?::dow, ?::time, ?::time)";

		try (Connection con = database_connection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, askingTeacherUid);
			ps.setInt(2, lectureId);
			ps.setString(3, newDow.toString());
			ps.setObject(4, newStartTime);
			ps.setObject(5, newEndTime);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DataInsertionException("Unexpected exception during PreparedStatement execution", e);
		}

	}

	@Override
	public void changeStatusOfCODR(int reviewingCoordId, int reqId, boolean isApproved) {
		final String sql = "UPDATE change_of_date_req SET reviewing_coord_id = ?, status = ?::request_status WHERE req_id = ?;";

		try (Connection con = database_connection.DbConnection.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, reviewingCoordId);
			ps.setString(2, isApproved ? "APPROVED" : "REJECTED");
			ps.setInt(3, reqId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DataUpdateException("Unexpected error during call of method changeStatusOfCODR", e);
		}

	}

}
