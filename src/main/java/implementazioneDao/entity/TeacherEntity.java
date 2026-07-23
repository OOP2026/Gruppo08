package implementazioneDao.entity;

public class TeacherEntity extends UserEntity {

	public TeacherEntity(
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
