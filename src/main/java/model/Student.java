package model;

public class Student extends User {
	private int studentId;
	private int academicYear;

	public Student(int studentId, int academicYear, int userId, String fname, String lname, String email, String login,
			String password) {
		super(userId, fname, lname, email, login, password);
		this.studentId = studentId;
		this.academicYear = academicYear;
	}

	public int getStudentId() {
		return studentId;
	}

	public int getAcademicYear() {
		return academicYear;
	}
}
