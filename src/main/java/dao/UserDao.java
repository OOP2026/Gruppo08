package dao;

import model.User;

import java.util.List;
import java.util.NoSuchElementException;

import daoImplementation.UserPostgresDao;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
	private static UserDao instance;

	private List<User> userInMem = new ArrayList<>();

	private UserDao() {
	}

	public static UserDao getInstance() {
		if (instance == null)
			instance = new UserDao();
		return instance;
	}

	public List<User> getUserInMem() {
		return new ArrayList<User>(userInMem);
	}

	private boolean isIdInMem(int userId) {
		for (User u : userInMem) {
			if (u.getUserId() == userId)
				return true;
		}
		return false;
	}

	private User getUserByIdInMem(int userId) throws NoSuchElementException {
		for (User u : userInMem) {
			if (u.getUserId() == userId)
				return u;
		}
		throw new NoSuchElementException(userId + " uid not found");
	}

	public User getUserById(int userId) throws NoSuchElementException {
		if (isIdInMem(userId)) {
			try {
				return getUserByIdInMem(userId);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		UserPostgresDao psqldao = new UserPostgresDao();
		User u = psqldao.getUserById(userId);
		userInMem.add(u);
		return u;
	}

	private boolean isLoginInMem(String login) {
		for (User u : userInMem) {
			if (u.getLogin().equals(login))
				return true;
		}
		return false;
	}

	private User getUserByLoginInMem(String login) throws NoSuchElementException {
		for (User u : userInMem) {
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

		UserPostgresDao psqldao = new UserPostgresDao();
		User u = psqldao.getUserByLogin(login);
		userInMem.add(u);
		return u;
	}

	private boolean isEmailInMem(String email) {
		for (User u : userInMem) {
			if (u.getEmail().equals(email))
				return true;
		}
		return false;
	}

	private User getUserByEmailInMem(String email) throws NoSuchElementException {
		for (User u : userInMem) {
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

		UserPostgresDao psqldao = new UserPostgresDao();
		User u = psqldao.getUserByEmail(email);
		userInMem.add(u);
		return u;
	}

	public void insertStudent(int academicYear, String fname, String lname, String email,
			String login, String password) throws SQLException {

		UserPostgresDao psqldao = new UserPostgresDao();

		User u = psqldao.insertStudent(academicYear, fname, lname, email, login, password);
		userInMem.add(u);

	}

	public void insertTeacher(boolean isCoordinator, String fname, String lname, String email,
			String login, String password) throws SQLException {

		UserPostgresDao psqldao = new UserPostgresDao();

		User u = psqldao.insertTeacher(isCoordinator, fname, lname, email, login, password);
		userInMem.add(u);

	}

}
