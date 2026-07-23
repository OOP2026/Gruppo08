package controller;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import controller.cache.LectureCache;
import controller.exception.DatabaseException;
import controller.exception.UnauthorizedException;
import dao.LectureDao;
import implementazioneDao.LecturePostgresDao;
import implementazioneDao.entity.LectureEntity;
import implementazioneDao.exception.DataRetrievalException;
import model.Classroom;
import model.Course;
import model.Lecture;

public class LectureService extends AbstractDaoService<Lecture, LectureEntity, Integer, LectureDao> {
	public LectureService() {
		super(new LecturePostgresDao());
	}

	@Override
	protected Lecture mapEntityToModel(LectureEntity e) {
		CourseService cService = new CourseService();
		Course course = cService.getById(e.getCourseId());
		ClassroomService classroomService = new ClassroomService();
		Classroom classroom = classroomService.getById(e.getClassroomName());
		return new Lecture(e.getId(), course, classroom, e.getDayofweek(), e.getStartTime(), e.getEndTime());
	}

	@Override
	public Lecture getById(Integer id) {
		return mapEntityToModel(LectureCache.getInstance().getById(id));
	}

	private List<Lecture> mapEntityListToModelList(List<LectureEntity> lecturesE) {
		List<Lecture> lectures = new ArrayList<>();
		for (LectureEntity le : lecturesE) {
			lectures.add(mapEntityToModel(le));
		}

		return lectures;
	}

	/**
	 * Crea una lezione interfacciandosi al DB e al dao in memory.
	 *
	 * @throws UnauthorizedException se l'utente non e' coordinatore
	 */
	public void makeLecture(int courseId, String classroomName, DayOfWeek dayofweek, LocalTime startTime,
			LocalTime endTime) {
		if (!SessionManager.getInstance().isCoordinator())
			throw new UnauthorizedException("This operation is restricted to coordinators only");

		dao.insertLecture(courseId, classroomName, dayofweek, startTime, endTime);
	}

	/**
	 * Restituisce una lista di lezioni di un anno accademico passato come
	 * parametro.
	 *
	 * @throws UnauthorizedException se l'utente non e' coordinatore
	 * @throws DatabaseException     se il db fallisce
	 */
	public List<Lecture> getAllByAcademicYear(int academicYear) throws IllegalStateException {
		List<LectureEntity> lecturesE;
		lecturesE = dao.getAllByAcademicYear(academicYear);

		if (lecturesE == null || lecturesE.isEmpty())
			throw new IllegalStateException("No lectures were found for academic year: " + academicYear);

		return mapEntityListToModelList(lecturesE);
	}

	/**
	 * Restituisce una lista di tutte le lezioni di un docente (Teacher).
	 * 
	 * @throws DatabaseException se il db fallisce
	 */
	public List<Lecture> getAllByTeacher(int teacherUid) {
		List<Lecture> lectures = new ArrayList<>();

		try {
			lectures = mapEntityListToModelList(dao.getAllByTeacher(teacherUid));
		} catch (DataRetrievalException e) {
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

		dao.changeLectureDate(lectureId, newDow, newStartTime, newEndTime);
	}

	public String[] getCols() {
		return new String[] { "Orario", DayOfWeek.MONDAY.toString(), DayOfWeek.TUESDAY.toString(),
				DayOfWeek.WEDNESDAY.toString(), DayOfWeek.THURSDAY.toString(), DayOfWeek.FRIDAY.toString(),
				DayOfWeek.SATURDAY.toString() };
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
