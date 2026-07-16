package dao;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import daoImplementation.LecturePostgresDao;
import model.Lecture;

public class LectureDao {
	private List<Lecture> lectureInMem = new ArrayList<>();
	private LecturePostgresDao sqldao = new LecturePostgresDao();

	private LectureDao() {
	}

	private static LectureDao instance;

	public static LectureDao getInstance() {
		if (instance == null)
			instance = new LectureDao();
		return instance;
	}

	public List<Lecture> getAllInMem() {
		return new ArrayList<>(lectureInMem);
	}

	private boolean isIdInMem(int lectureId) {
		for (Lecture l : lectureInMem) {
			if (l.getLectureId() == lectureId)
				return true;
		}
		return false;
	}

	private Lecture getByIdInMem(int lectureId) {
		for (Lecture l : lectureInMem) {
			if (l.getLectureId() == lectureId)
				return l;
		}
		throw new NoSuchElementException(lectureId + " lecture id not found");
	}

	public Lecture getById(int lectureId) throws NoSuchElementException {
		if (isIdInMem(lectureId)) {
			try {
				return getByIdInMem(lectureId);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		Lecture l = sqldao.getById(lectureId);
		lectureInMem.add(l);
		return l;
	}

	public List<Lecture> getAllByAcademicYear(int academicYear) throws SQLException {
		return sqldao.getAllByAcademicYear(academicYear);
	}

	public void insertLecture(int courseId, String classroomName, DayOfWeek dayofweek, LocalTime startTime,
			LocalTime endTime) throws SQLException {
		Lecture l = sqldao.insertLecture(courseId, classroomName, dayofweek, startTime, endTime);
		lectureInMem.add(l);
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
