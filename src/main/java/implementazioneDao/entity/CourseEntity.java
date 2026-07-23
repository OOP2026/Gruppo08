package implementazioneDao.entity;

public class CourseEntity implements IdentifiableEntity<Integer> {
	public CourseEntity(int courseId, int teacherUid, String name, int cfu, int academicYear, boolean isActive) {
		this.academicYear = academicYear;
		this.isActive = isActive;
		this.name = name;
		this.teacherUid = teacherUid;
		this.courseId = courseId;
		this.cfu = cfu;
	}

	private int courseId;
	private int teacherUid;
	private String name;
	private int cfu;
	private int academicYear;
	private boolean isActive;

	@Override
	public Integer getId() {
		return courseId;
	}

	public int getTeacherUid() {
		return teacherUid;
	}

	public String getName() {
		return name;
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
