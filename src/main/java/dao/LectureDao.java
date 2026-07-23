package dao;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import implementazioneDao.entity.LectureEntity;

public interface LectureDao extends GenericDao<LectureEntity, Integer> {
	public List<LectureEntity> getAllByAcademicYear(int academicYear);

	public void insertLecture(int courseId, String classroomName, DayOfWeek dayofweek, LocalTime startTime,
			LocalTime endTime);

	public List<LectureEntity> getAllByTeacher(int teacherUid);

	public void changeLectureDate(int lectureId, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime);
}
