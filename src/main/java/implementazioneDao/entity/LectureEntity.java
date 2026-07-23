package implementazioneDao.entity;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class LectureEntity implements IdentifiableEntity<Integer> {
	public LectureEntity(
			int lectureId,
			Integer courseId,
			String classroomName,
			DayOfWeek dayofweek,
			LocalTime startTime,
			LocalTime endTime) {
		this.lectureId = lectureId;
		this.courseId = courseId;
		this.classroomName = classroomName;
		this.dayofweek = dayofweek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	private int lectureId;
	private Integer courseId;
	private String classroomName;
	private DayOfWeek dayofweek;
	private LocalTime startTime;
	private LocalTime endTime;

	@Override
	public Integer getId() {
		return lectureId;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public DayOfWeek getDayofweek() {
		return dayofweek;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public String getClassroomName() {
		return classroomName;
	}
}
