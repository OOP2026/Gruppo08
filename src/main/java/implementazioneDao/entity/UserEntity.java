package implementazioneDao.entity;

public class UserEntity implements IdentifiableEntity<Integer> {
	public UserEntity(
			int userId,
			String fname,
			String lname,
			String email,
			String login,
			String password) {
		this.userId = userId;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.login = login;
		this.password = password;
	}

	private int userId;
	private String fname;
	private String lname;
	private String email;
	private String login;
	private String password;

	@Override
	public Integer getId() {
		return userId;
	}

	public String getPassword() {
		return password;
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
