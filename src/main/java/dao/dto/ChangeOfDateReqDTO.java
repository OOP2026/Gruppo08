package dao.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ChangeOfDateReqDTO implements IdentifiableDTO<Integer> {
	private Integer reqId;
	private Integer askingTeacherUid;
	private Integer reviewingCoordUid;
	private Integer lectureId;
	private DayOfWeek newDow;
	private LocalTime newStartTime;
	private LocalTime newEndTime;
	private String unformattedStatus;

	public ChangeOfDateReqDTO(
			Integer reqId,
			Integer askingTeacherUid,
			Integer reviewingCoordUid,
			Integer lectureId,
			DayOfWeek newDow,
			LocalTime newStartTime,
			LocalTime newEndTime,
			String unformattedStatus) {
		this.reqId = reqId;
		this.askingTeacherUid = askingTeacherUid;
		this.reviewingCoordUid = reviewingCoordUid;
		this.lectureId = lectureId;
		this.newDow = newDow;
		this.newStartTime = newStartTime;
		this.newEndTime = newEndTime;
		this.unformattedStatus = unformattedStatus;
	}

	public String getUnformattedStatus() {
		return unformattedStatus;
	}

	public Integer getReviewingCoordUid() {
		return reviewingCoordUid;
	}

	@Override
	public Integer getId() {
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

	public Integer getLectureId() {
		return lectureId;
	}

	public Integer getAskingTeacherUid() {
		return askingTeacherUid;
	}
}
