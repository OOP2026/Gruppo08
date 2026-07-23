package dao.dto;

public class StudentDTO extends UserDTO {

	public StudentDTO(
			int user_id,
			String fname,
			String lname,
			String email,
			String login,
			String password,
			int studentId,
			int academicYear) {
		super(user_id, fname, lname, email, login, password);
		this.studentId = studentId;
		this.academicYear = academicYear;
	}

	private int studentId;
	private int academicYear;

	public int getStudentId() {
		return studentId;
	}

	public int getAcademicYear() {
		return academicYear;
	}
}
