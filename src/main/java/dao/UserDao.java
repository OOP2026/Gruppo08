package dao;

import model.User;

import java.util.List;
import java.util.NoSuchElementException;

import daoImplementation.UserPostgresDao;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao extends AbstractDao<User, UserPostgresDao, Integer> {
	private static UserDao instance;

	private UserDao() {
		super(new UserPostgresDao());
	}

	public static UserDao getInstance() {
		if (instance == null)
			instance = new UserDao();
		return instance;
	}

	public List<User> getUserInMem() {
		return new ArrayList<User>(inMem);
	}

	private boolean isLoginInMem(String login) {
		for (User u : inMem) {
			if (u.getLogin().equals(login))
				return true;
		}
		return false;
	}

	private User getUserByLoginInMem(String login) throws NoSuchElementException {
		for (User u : inMem) {
			if (u.getLogin().equals(login))
				return u;
		}
		throw new NoSuchElementException(login + " not found");
	}

	public User getUserByLogin(String login) throws NoSuchElementException {
		if (isLoginInMem(login)) {
			try {
				return getUserByLoginInMem(login);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		User u = sqldao.getUserByLogin(login);
		inMem.add(u);
		return u;
	}

	private boolean isEmailInMem(String email) {
		for (User u : inMem) {
			if (u.getEmail().equals(email))
				return true;
		}
		return false;
	}

	private User getUserByEmailInMem(String email) throws NoSuchElementException {
		for (User u : inMem) {
			if (u.getEmail().equals(email))
				return u;
		}
		throw new NoSuchElementException(email + " not found");
	}

	public User getUserByEmail(String email) throws NoSuchElementException {
		if (isEmailInMem(email)) {
			try {
				return getUserByEmailInMem(email);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		User u = sqldao.getUserByEmail(email);
		inMem.add(u);
		return u;
	}

	public void insertStudent(int academicYear, String fname, String lname, String email,
			String login, String password) throws SQLException {
		User u = sqldao.insertStudent(academicYear, fname, lname, email, login, password);
		inMem.add(u);

	}

	public void insertTeacher(boolean isCoordinator, String fname, String lname, String email,
			String login, String password) throws SQLException {
		User u = sqldao.insertTeacher(isCoordinator, fname, lname, email, login, password);
		inMem.add(u);

	}

}
