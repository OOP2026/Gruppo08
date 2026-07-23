package dao.dto;

public class UserDTO implements IdentifiableDTO<Integer> {
	public UserDTO(
			int user_id,
			String fname,
			String lname,
			String email,
			String login,
			String password) {
		this.user_id = user_id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.login = login;
		this.password = password;
	}

	private int user_id;
	private String fname;
	private String lname;
	private String email;
	private String login;
	private String password;

	@Override
	public Integer getId() {
		return user_id;
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
