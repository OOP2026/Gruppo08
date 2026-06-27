package model;

public class Teacher extends User {
	private boolean isCoordinator;

	public Teacher(boolean isCoordinator, int userId, String fname, String lname, String email, String login,
			String password) {
		super(userId, fname, lname, email, login, password);
		this.isCoordinator = isCoordinator;
	}

	public boolean isCoordinator() {
		return isCoordinator;
	}
}
