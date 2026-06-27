package controller;

import model.User;
import model.Student;
import model.Teacher;

public class SessionManager {
	private static SessionManager instance;

	private User session;
	private UserRole role;

	private SessionManager() {
	}

	public static SessionManager getInstance() {
		if (instance == null)
			instance = new SessionManager();

		return instance;
	}

	public User getSession() {
		return session;
	}

	public void setSession(User session) {
		this.session = session;

		if (session instanceof Student)
			role = UserRole.STUDENT;
		else if (session instanceof Teacher) {
			role = UserRole.TEACHER;
			if (((Teacher) session).isCoordinator())
				role = UserRole.COORDINATOR;
		}

	}

	public UserRole getRole() {
		return role;
	}

	public boolean isCoordinator() {
		return getRole() == UserRole.COORDINATOR;
	}

	public boolean isTeacher() {
		return isCoordinator() || getRole() == UserRole.TEACHER;
	}

	public boolean isStudent() {
		return getRole() == UserRole.STUDENT;
	}

}
