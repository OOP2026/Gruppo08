package dao;

import java.util.NoSuchElementException;

import dao.impl.CoursePostgresDao;
import model.Course;

public class CourseDao extends AbstractDao<Course, CoursePostgresDao, Integer> {
	private static CourseDao instance;

	private CourseDao() {
		super(new CoursePostgresDao());
	}

	public static CourseDao getInstance() {
		if (instance == null)
			instance = new CourseDao();
		return instance;
	}

	private boolean isNameNYearInMem(String name, int academicYear) {
		for (Course c : inMem) {
			if (c.getName().equals(name) && c.getAcademicYear() == academicYear)
				return true;
		}
		return false;
	}

	private Course getCourseByNameNYearInMem(String name, int academicYear) throws NoSuchElementException {
		for (Course c : inMem) {
			if (c.getName().equals(name) && c.getAcademicYear() == academicYear)
				return c;
		}
		throw new NoSuchElementException("course not found " + name + academicYear);
	}

	public Course getByNameNYear(String name, int academicYear) {
		if (isNameNYearInMem(name, academicYear)) {
			try {
				return getCourseByNameNYearInMem(name, academicYear);
			} catch (NoSuchElementException e) {
				throw new IllegalStateException(
						"Memory corruption on method call getByNameNYear(" + name + "," + academicYear + ")", e);
			}
		}

		Course c = sqldao.getByNameNYear(name, academicYear);
		inMem.add(c);
		return c;
	}

	public void insertCourse(int teacherUid, String name, int cfu, int academicYear, boolean isActive) {
		Course c = sqldao.insertCourse(teacherUid, name, cfu, academicYear, isActive);
		inMem.add(c);
	}
}
