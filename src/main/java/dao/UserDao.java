package dao;

import implementazioneDao.entity.TeacherEntity;
import implementazioneDao.entity.UserEntity;
import java.sql.SQLException;
import java.util.List;

public interface UserDao extends GenericDao<UserEntity, Integer> {
	public UserEntity getUserByLogin(String login);

	public UserEntity getUserByEmail(String email);

	public void insertStudent(int academicYear, String fname, String lname, String email,
			String login, String password) throws SQLException;

	public void insertTeacher(boolean isCoordinator, String fname, String lname, String email,
			String login, String password) throws SQLException;

	public List<TeacherEntity> getAllTeachers();
}
