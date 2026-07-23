package dao.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class LectureDTO implements IdentifiableDTO<Integer> {
	public LectureDTO(
			int lecture_id,
			Integer course_id,
			String classroom_name,
			DayOfWeek dayofweek,
			LocalTime start_time,
			LocalTime end_time) {
		this.lecture_id = lecture_id;
		this.course_id = course_id;
		this.classroom_name = classroom_name;
		this.dayofweek = dayofweek;
		this.start_time = start_time;
		this.end_time = end_time;
	}

	private int lecture_id;
	private Integer course_id;
	private String classroom_name;
	private DayOfWeek dayofweek;
	private LocalTime start_time;
	private LocalTime end_time;

	@Override
	public Integer getId() {
		return lecture_id;
	}

	public LocalTime getStart_time() {
		return start_time;
	}

	public LocalTime getEnd_time() {
		return end_time;
	}

	public DayOfWeek getDayofweek() {
		return dayofweek;
	}

	public Integer getCourse_id() {
		return course_id;
	}

	public String getClassroom_name() {
		return classroom_name;
	}
}
