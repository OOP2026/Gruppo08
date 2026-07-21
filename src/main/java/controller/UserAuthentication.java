package controller;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import javax.security.sasl.AuthenticationException;

import model.User;
import dao.UserDao;

public class UserAuthentication extends AbstractDaoService<UserDao> {
	public UserAuthentication() {
		super(UserDao.getInstance());
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
				u = dao.getUserByEmail(identifier);
			else
				u = dao.getUserByLogin(identifier);
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
}
