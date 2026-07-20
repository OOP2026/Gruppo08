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

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public void setDayofweek(DayOfWeek dayofweek) {
		this.dayofweek = dayofweek;
	}

	@Override
	public String toString() {
		return lectureId + " " + course.getName() + " " + getTimeInterval();
	}
}
