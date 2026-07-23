package model;

public abstract class User implements Identifiable<Integer> {
	private int userId;
	private String fname;
	private String lname;
	private String email;
	private String login;
	private String password;

	protected User(int userId, String fname, String lname, String email, String login, String password) {
		this.userId = userId;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.login = login;
		this.password = password;
	}

	@Override
	public Integer getId() {
		return userId;
	}

	@Override
	public String toString() {
		return Integer.toString(userId) + " " + fname + " " + lname;
	}

	public boolean checkPswd(String password) {
		return password.equals(this.password);
	}

	public String getLogin() {
		return login;
	}

	public String getLname() {
		return lname;
	}

	public String getFname() {
		return fname;
	}

	public String getEmail() {
		return email;
	}
}
