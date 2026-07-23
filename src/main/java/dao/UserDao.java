package dao;

import dao.dto.UserDTO;
import java.sql.SQLException;

public interface UserDao extends GenericDao<UserDTO, Integer> {
	public UserDTO getUserByLogin(String login);

	public UserDTO getUserByEmail(String email);

	public void insertStudent(int academicYear, String fname, String lname, String email,
			String login, String password) throws SQLException;

	public void insertTeacher(boolean isCoordinator, String fname, String lname, String email,
			String login, String password) throws SQLException;

}
