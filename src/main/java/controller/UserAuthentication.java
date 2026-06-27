package controller;

import javax.security.sasl.AuthenticationException;

import model.User;
import dao.UserDao;

public class UserAuthentication {
	public User login(String identifier, String password) throws AuthenticationException {
		User u;
		UserDao udao = UserDao.getInstance();

		try {
			if (identifier.contains("@"))
				u = udao.getUserByEmail(identifier);
			else
				u = udao.getUserByLogin(identifier);
		} catch (Exception e) {
			throw new AuthenticationException("incorrect login/pswd");
		}

		if (!u.checkPswd(password))
			throw new AuthenticationException("incorrect login/pswd");

		SessionManager sm = SessionManager.getInstance();
		sm.setSession(u);
		return u;
	}

	public void register(int academicYear, String fname, String lname, String email, String login, String password)
			throws AuthenticationException {
		UserDao udao = UserDao.getInstance();
		try {
			udao.insertStudent(academicYear, fname, lname, email, login, password);
		} catch (Exception e) {
			throw new AuthenticationException("unable to register");
		}
	}

	public void register(boolean isCoordinator, String fname, String lname, String email, String login, String password)
			throws AuthenticationException {
		UserDao udao = UserDao.getInstance();
		try {
			udao.insertTeacher(isCoordinator, fname, lname, email, login, password);
		} catch (Exception e) {
			throw new AuthenticationException("unable to register");
		}
	}
}
