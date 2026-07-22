package controller;

import controller.exception.DatabaseException;
import controller.exception.UnauthorizedException;
import dao.CourseDao;

public class CourseService extends AbstractDaoService<CourseDao> {
	public CourseService() {
		super(CourseDao.getInstance());
	}

	/**
	 * Metodo per creare un corso di insegnamento
	 *
	 * @throws UnauthorizedException se l'utente non è un coordinatore.
	 * @throws DatabaseException     se il DB fallisce.
	 */
	public void makeCourse(int teacherUid, String name, int cfu, int academicYear, boolean isActive) {
		if (!SessionManager.getInstance().isCoordinator())
			throw new UnauthorizedException("This operation is restricted to coordinators only");

		dao.insertCourse(teacherUid, name, cfu, academicYear, isActive);
	}

}
