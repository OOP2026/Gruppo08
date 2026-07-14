package model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Lecture {
	private int lectureId;
	private Course course;
	private Classroom classroom;
	private DayOfWeek dayofweek;
	private LocalTime startTime;
	private LocalTime endTime;

	public Lecture(int lectureId, Course course, Classroom classroom, DayOfWeek dayofweek, LocalTime startTime,
			LocalTime endTime) {
		this.lectureId = lectureId;
		this.course = course;
		this.classroom = classroom;
		this.dayofweek = dayofweek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public int getLectureId() {
		return lectureId;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public DayOfWeek getDayofweek() {
		return dayofweek;
	}

	public Course getCourse() {
		return course;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public String getTimeInterval() {
		return startTime.toString() + " - " + endTime.toString();
	}
}
