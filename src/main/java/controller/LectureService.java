package controller;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import dao.LectureDao;
import model.Lecture;

public class LectureService {
	private final String[] cols = { "Orario", DayOfWeek.MONDAY.toString(), DayOfWeek.TUESDAY.toString(),
			DayOfWeek.WEDNESDAY.toString(), DayOfWeek.THURSDAY.toString(), DayOfWeek.FRIDAY.toString(),
			DayOfWeek.SATURDAY.toString() };

	public String[] getCols() {
		return cols;
	}
	
	public void makeLecture(int courseId, String classroomName, DayOfWeek dayofweek, LocalTime startTime,
			LocalTime endTime) throws IllegalStateException {
		if (!SessionManager.getInstance().isCoordinator())
			throw new IllegalStateException("This operation is restricted to coordinators only");

		LectureDao ldao = LectureDao.getInstance();
		try {
			ldao.insertLecture(courseId, classroomName, dayofweek, startTime, endTime);
		} catch (SQLException e) {
			throw new IllegalStateException(
					"unable to insert lecture " + courseId + classroomName + dayofweek + startTime + endTime);
		}

	}

	public List<Lecture> getAllByAcademicYear(int academicYear) throws IllegalStateException {
		List<Lecture> lectures;
		try {
			lectures = LectureDao.getInstance().getAllByAcademicYear(academicYear);
		} catch (SQLException e) {
			throw new IllegalStateException("Unexpected error while retrieving lectures for academic year: " + academicYear);
		}

		if (lectures == null || lectures.isEmpty())
			throw new IllegalStateException("No lectures were found for academic year: " + academicYear);

		return lectures;
	}

	public Object[][] getLecturesMtx(int academicYear) {
		List<Lecture> lectures = getAllByAcademicYear(academicYear);
		lectures.sort(Comparator.comparing(Lecture::getStartTime));

		ArrayList<String[]> rows = new ArrayList<>();
		String[] currentRow = null;
		Object currentTime = null;

		for (Lecture lecture : lectures) {
			if (currentRow == null || !lecture.getStartTime().equals(currentTime)) {
				currentRow = new String[cols.length];
				currentRow[0] = lecture.getTimeInterval();
				rows.add(currentRow);
				currentTime = lecture.getStartTime();
			}

			int dayIdx = -1;
			for (int j = 1; j < cols.length; j++) {
				if (cols[j].equals(lecture.getDayofweek().toString())) {
					dayIdx = j;
					break;
				}
			}

			if (dayIdx != -1) {
				currentRow[dayIdx] = lecture.getCourse().getName();
			}
		}

		Object[][] mtx = new Object[rows.size()][cols.length];
		for (int i = 0; i < rows.size(); i++) {
			mtx[i] = rows.get(i);
		}

		return mtx;
	}
}
