package model;

public class Course {
	private int courseId;
	private Teacher teacher;
	private String name;
	private int cfu;
	private int academicYear;
	private boolean isActive;

	public Course(int courseId, Teacher teacher, String name, int cfu, int academicYear, boolean isActive) {
		this.courseId = courseId;
		this.teacher = teacher;
		this.name = name;
		this.cfu = cfu;
		this.academicYear = academicYear;
		this.isActive = isActive;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public String getName() {
		return name;
	}

	public int getCourseId() {
		return courseId;
	}

	public int getCfu() {
		return cfu;
	}

	public int getAcademicYear() {
		return academicYear;
	}

	public boolean isActive() {
		return isActive;
	}
}
