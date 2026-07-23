package dao.dto;

public class TeacherDTO extends UserDTO {

	public TeacherDTO(
			int userId,
			String fname,
			String lname,
			String email,
			String login,
			String password,
			boolean isCoordinator) {
		super(userId, fname, lname, email, login, password);
		this.isCoordinator = isCoordinator;
	}

	private boolean isCoordinator;

	public boolean isCoordinator() {
		return isCoordinator;
	}
}
