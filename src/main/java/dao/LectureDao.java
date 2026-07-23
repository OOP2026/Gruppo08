package dao;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import dao.dto.LectureDTO;

public interface LectureDao extends GenericDao<LectureDTO, Integer> {
	public List<LectureDTO> getAllByAcademicYear(int academicYear);

	public void insertLecture(int courseId, String classroomName, DayOfWeek dayofweek, LocalTime startTime,
			LocalTime endTime);

	public List<LectureDTO> getAllByTeacher(int teacherUid);

	public void changeLectureDate(int lectureId, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime);
}
