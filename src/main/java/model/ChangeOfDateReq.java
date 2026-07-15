package model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ChangeOfDateReq {
	private int reqId;
	private Teacher askingTeacher;
	private Teacher reviewingCoord;
	private Lecture lecture;
	private DayOfWeek newDow;
	private LocalTime newStartTime;
	private LocalTime newEndTime;
	private RequestStatus status;

	public ChangeOfDateReq(int reqId, Teacher askingTeacher, Teacher reviewingCoord, Lecture lecture, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime, RequestStatus status) {
		this.reqId = reqId;
		this.askingTeacher = askingTeacher;
		this.reviewingCoord = reviewingCoord;
		this.lecture = lecture;
		this.newDow = newDow;
		this.newStartTime = newStartTime;
		this.newEndTime = newEndTime;
		this.status = status;
	}

	public String getStatusStr() {
		return status.toString();
	}

	public Teacher getReviewingCoord() {
		return reviewingCoord;
	}

	public int getReqId() {
		return reqId;
	}

	public LocalTime getNewStartTime() {
		return newStartTime;
	}

	public LocalTime getNewEndTime() {
		return newEndTime;
	}

	public DayOfWeek getNewDow() {
		return newDow;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public Teacher getAskingTeacher() {
		return askingTeacher;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

}
