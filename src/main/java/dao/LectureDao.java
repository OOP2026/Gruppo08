package dao;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import daoImplementation.LecturePostgresDao;
import model.Lecture;

public class LectureDao extends AbstractDao<Lecture, LecturePostgresDao, Integer> {
	private LectureDao() {
		super(new LecturePostgresDao());
	}

	private static LectureDao instance;

	public static LectureDao getInstance() {
		if (instance == null)
			instance = new LectureDao();
		return instance;
	}

	public List<Lecture> getAllByAcademicYear(int academicYear) throws SQLException {
		return sqldao.getAllByAcademicYear(academicYear);
	}

	public void insertLecture(int courseId, String classroomName, DayOfWeek dayofweek, LocalTime startTime,
			LocalTime endTime) throws SQLException {
		Lecture l = sqldao.insertLecture(courseId, classroomName, dayofweek, startTime, endTime);
		inMem.add(l);
	}

	public List<Lecture> getAllByTeacher(int teacherUid) throws SQLException {
		return sqldao.getAllByTeacher(teacherUid);
	}

	public void changeLectureDate(int lectureId, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime) throws SQLException {
		Lecture l = getById(lectureId);

		sqldao.updateLectureDate(lectureId, newDow, newStartTime, newEndTime);

		l.setStartTime(newStartTime);
		l.setDayofweek(newDow);
		l.setEndTime(newEndTime);
	}
}
