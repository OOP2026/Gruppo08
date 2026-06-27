package controller;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import dao.LectureDao;
import model.Lecture;

public class LectureService {
	public void makeLecture(int courseId, String classroomName, DayOfWeek dayofweek, LocalTime startTime,
			LocalTime endTime) throws RuntimeException {
		if (!SessionManager.getInstance().isCoordinator())
			throw new RuntimeException("This operation is restricted to coordinators only");

		LectureDao ldao = LectureDao.getInstance();
		try {
			ldao.insertLecture(courseId, classroomName, dayofweek, startTime, endTime);
		} catch (SQLException e) {
			throw new RuntimeException(
					"unable to insert lecture " + courseId + classroomName + dayofweek + startTime + endTime);
		}

	}

	public List<Lecture> getAllByAcademicYear(int academicYear) throws RuntimeException {
		List<Lecture> lectures = null;
		try {
			lectures = LectureDao.getInstance().getAllByAcademicYear(academicYear);
		} catch (SQLException e) {
			throw new RuntimeException("Unexpected error while retrieving lectures for academic year: " + academicYear);
		}

		if (lectures == null || lectures.isEmpty())
			throw new RuntimeException("No lectures were found for academic year: " + academicYear);

		return lectures;
	}
}
