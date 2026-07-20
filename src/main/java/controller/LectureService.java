package controller;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import controller.exception.DatabaseException;
import controller.exception.UnauthorizedException;
import dao.LectureDao;
import model.Lecture;

public class LectureService {
	private final LectureDao ldao = LectureDao.getInstance();

	/**
	 * Crea una lezione interfacciandosi al DB e al dao in memory.
	 *
	 * @throws UnauthorizedException se l'utente non e' coordinatore
	 * @throws DatabaseException     se il db fallisce
	 */
	public void makeLecture(int courseId, String classroomName, DayOfWeek dayofweek, LocalTime startTime,
			LocalTime endTime) {
		if (!SessionManager.getInstance().isCoordinator())
			throw new UnauthorizedException("This operation is restricted to coordinators only");

		try {
			ldao.insertLecture(courseId, classroomName, dayofweek, startTime, endTime);
		} catch (SQLException e) {
			throw new DatabaseException(
					"unable to insert lecture " + courseId + classroomName + dayofweek + startTime + endTime, e);
		}

	}

	/**
	 * Restituisce una lista di lezioni di un anno accademico passato come
	 * parametro.
	 *
	 * @throws UnauthorizedException se l'utente non e' coordinatore
	 * @throws DatabaseException     se il db fallisce
	 */
	public List<Lecture> getAllByAcademicYear(int academicYear) throws IllegalStateException {
		List<Lecture> lectures;
		try {
			lectures = LectureDao.getInstance().getAllByAcademicYear(academicYear);
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Unexpected error while retrieving lectures for academic year: " + academicYear);
		}

		if (lectures == null || lectures.isEmpty())
			throw new IllegalStateException("No lectures were found for academic year: " + academicYear);

		return lectures;
	}

	/**
	 * Restituisce una lista di tutte le lezioni di un docente (Teacher).
	 * 
	 * @throws DatabaseException se il db fallisce
	 */
	public List<Lecture> getAllByTeacher(int teacherUid) {
		List<Lecture> lectures = new ArrayList<>();

		try {
			lectures = ldao.getAllByTeacher(teacherUid);
		} catch (SQLException e) {
			throw new DatabaseException("Unable to retrieve lectures of teacher with uid: " + teacherUid, e);
		}

		return lectures;
	}

	/**
	 * Metodo analogo a getAllByTeacher,
	 * restituisce una lista di tutte le lezioni di un docente (Teacher)
	 *
	 * @return lista di stringhe che rappresentano le lezioni
	 * @throws DatabaseException se il recupero dei dati dal database fallisce
	 */
	public List<String> getAllByTeacherToString(int teacherUid) {
		List<Lecture> lectures = getAllByTeacher(teacherUid);
		List<String> lecturesString = new ArrayList<>();

		for (Lecture l : lectures)
			lecturesString.add(l.toString());

		return lecturesString;
	}

	/**
	 * Cambia la data di una lezione.
	 *
	 * @param lectureId lezione da modificare
	 * @throws UnauthorizedException se l'utente in session non e' coordinatore
	 */
	public void changeLectureDate(int lectureId, DayOfWeek newDow,
			LocalTime newStartTime, LocalTime newEndTime) {
		if (!SessionManager.getInstance().isCoordinator())
			throw new UnauthorizedException("This operation is restricted to coordinators only");

		try {
			ldao.changeLectureDate(lectureId, newDow, newStartTime, newEndTime);
		} catch (SQLException e) {
			throw new DatabaseException("unable to update lecture with id " + lectureId, e);
		}
	}

	public String[] getCols() {
		final String[] cols = { "Orario", DayOfWeek.MONDAY.toString(), DayOfWeek.TUESDAY.toString(),
				DayOfWeek.WEDNESDAY.toString(), DayOfWeek.THURSDAY.toString(), DayOfWeek.FRIDAY.toString(),
				DayOfWeek.SATURDAY.toString() };
		return cols;
	}

	/**
	 * @param academicYear
	 * @return matrice delle lezioni ordinate da utilizzare nella gui
	 * @throws UnauthorizedException se l'utente non e' coordinatore
	 * @throws DatabaseException     se il db fallisce
	 */
	public Object[][] getLecturesMtx(int academicYear) {
		final String[] cols = getCols();

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
