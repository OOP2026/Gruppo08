package controller;

import controller.cache.CourseCache;
import controller.exception.DatabaseException;
import controller.exception.UnauthorizedException;
import dao.CourseDao;
import implementazioneDao.CoursePostgresDao;
import implementazioneDao.entity.CourseEntity;
import implementazioneDao.exception.DataInsertionException;
import model.Course;
import model.Teacher;

import java.util.ArrayList;
import java.util.List;

public class CourseService extends AbstractDaoService<Course, CourseEntity, Integer, CourseDao> {
	public CourseService() {
		super(new CoursePostgresDao());
	}

	@Override
	protected Course mapEntityToModel(CourseEntity e) {
		UserService uauth = new UserService();
		Teacher teacher = (Teacher) uauth.getById(e.getTeacherUid());
		return new Course(e.getId(), teacher, e.getName(), e.getCfu(), e.getAcademicYear(), e.isActive());
	}

	@Override
	public Course getById(Integer id) {
		return mapEntityToModel(CourseCache.getInstance().getById(id));
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

		try {
			dao.insertCourse(teacherUid, name, cfu, academicYear, isActive);
		} catch (DataInsertionException e) {
			throw new DatabaseException("Inseriti dati non validi", e);
		}
	}

	public List<String> getAllCoursesInfo() {
		List<CourseEntity> coursesE = dao.getAllActiveCourses();

		List<String> r = new ArrayList<>();
		for (CourseEntity ce : coursesE) {
			Course c = mapEntityToModel(ce);
			r.add(c.toString());
		}

		return r;
	}

}
