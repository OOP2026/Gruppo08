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

		LecturePostgresDao psqldao = new LecturePostgresDao();
		Lecture l = psqldao.getById(lectureId);
		lectureInMem.add(l);
		return l;
	}

	public List<Lecture> getAllByAcademicYear(int academicYear) throws SQLException {
		LecturePostgresDao psqldao = new LecturePostgresDao();
		return psqldao.getAllByAcademicYear(academicYear);
	}

	public void insertLecture(int courseId, String classroomName, DayOfWeek dayofweek, LocalTime startTime,
			LocalTime endTime) throws SQLException {
		LecturePostgresDao psqldao = new LecturePostgresDao();
		Lecture l = psqldao.insertLecture(courseId, classroomName, dayofweek, startTime, endTime);
		lectureInMem.add(l);
	}
}
