package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.security.sasl.AuthenticationException;
import controller.cache.UserCache;
import model.Student;
import model.Teacher;
import model.User;
import dao.UserDao;
import implementazioneDao.UserPostgresDao;
import implementazioneDao.entity.StudentEntity;
import implementazioneDao.entity.TeacherEntity;
import implementazioneDao.entity.UserEntity;

public class UserService extends AbstractDaoService<User, UserEntity, Integer, UserDao> {
	public UserService() {
		super(new UserPostgresDao());
	}

	@Override
	public User mapEntityToModel(UserEntity e) {
		User u = null;

		if (e instanceof StudentEntity) {
			StudentEntity se = (StudentEntity) e;
			u = new Student(se.getStudentId(), se.getAcademicYear(), se.getId(), se.getFname(), se.getLname(),
					se.getEmail(), se.getLogin(), se.getPassword());
		} else if (e instanceof TeacherEntity) {
			TeacherEntity te = (TeacherEntity) e;
			u = new Teacher(te.isCoordinator(), te.getId(), te.getFname(), te.getLname(), te.getEmail(), te.getLogin(),
					te.getPassword());
		}

		return u;
	}

	@Override
	public User getById(Integer id) {
		return mapEntityToModel(UserCache.getInstance().getById(id));
	}

	/**
	 * Modifica "session" di SessionManager con l'utente identificato da
	 * "identifier" se la password e' corretta.
	 *
	 * @param identifier puo' essere login o email di un User
	 * @throws AuthenticationException
	 */
	public User login(String identifier, String password) throws AuthenticationException {
		User u;

		try {
			if (identifier.contains("@"))
				u = mapEntityToModel(dao.getUserByEmail(identifier));
			else
				u = mapEntityToModel(dao.getUserByLogin(identifier));
		} catch (NoSuchElementException e) {
			throw new AuthenticationException("incorrect login/pswd");
		}

		if (!u.checkPswd(password))
			throw new AuthenticationException("incorrect login/pswd");

		SessionManager sm = SessionManager.getInstance();
		sm.setSession(u);
		return u;
	}

	/**
	 * Registra uno studente
	 * 
	 * @throws AuthenticationException
	 */
	public void register(int academicYear, String fname, String lname, String email, String login, String password)
			throws AuthenticationException {
		try {
			dao.insertStudent(academicYear, fname, lname, email, login, password);
		} catch (SQLException e) {
			throw new AuthenticationException("unable to register");
		}
	}

	/**
	 * Registra un docente
	 * 
	 * @param isCoordinator se true registra un docente coordinatore
	 * @throws AuthenticationException
	 */
	public void register(boolean isCoordinator, String fname, String lname, String email, String login, String password)
			throws AuthenticationException {
		try {
			dao.insertTeacher(isCoordinator, fname, lname, email, login, password);
		} catch (SQLException e) {
			throw new AuthenticationException("unable to register");
		}
	}

	public List<String> getAllTeachersInfo() {
		List<TeacherEntity> tes = dao.getAllTeachers();
		List<User> users = new ArrayList<>();

		for (TeacherEntity t : tes)
			users.add(mapEntityToModel(t));

		List<String> r = new ArrayList<>();

		for (User u : users)
			r.add(u.toString());

		return r;
	}
}
