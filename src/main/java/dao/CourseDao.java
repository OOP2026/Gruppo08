package dao;

import model.Course;

import java.util.List;
import java.util.NoSuchElementException;

import daoImplementation.CoursePostgresDao;

import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDao {
	private static CourseDao instance;

	private List<Course> courseInMem = new ArrayList<>();

	private CourseDao() {
	}

	public static CourseDao getInstance() {
		if (instance == null)
			instance = new CourseDao();
		return instance;
	}

	public List<Course> getCourseInMem() {
		return new ArrayList<>(courseInMem);
	}

	private boolean isIdInMem(int courseId) {
		for (Course c : courseInMem) {
			if (c.getCourseId() == courseId)
				return true;
		}
		return false;
	}

	private Course getCourseByIdInMem(int courseId) throws NoSuchElementException {
		for (Course c : courseInMem) {
			if (c.getCourseId() == courseId)
				return c;
		}
		throw new NoSuchElementException(courseId + " course_id not found");
	}

	public Course getCourseById(int courseId) throws NoSuchElementException {
		if (isIdInMem(courseId)) {
			try {
				return getCourseByIdInMem(courseId);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		CoursePostgresDao psqldao = new CoursePostgresDao();
		Course c = psqldao.getById(courseId);
		courseInMem.add(c);
		return c;
	}

	private boolean isNameNYearInMem(String name, int academicYear) {
		for (Course c : courseInMem) {
			if (c.getName().equals(name) && c.getAcademicYear() == academicYear)
				return true;
		}
		return false;
	}

	private Course getCourseByNameNYearInMem(String name, int academicYear) throws NoSuchElementException {
		for (Course c : courseInMem) {
			if (c.getName().equals(name) && c.getAcademicYear() == academicYear)
				return c;
		}
		throw new NoSuchElementException("course not found " + name + academicYear);
	}

	public Course getByNameNYear(String name, int academicYear) {
		if (isNameNYearInMem(name, academicYear)) {
			try {
				return getCourseByNameNYearInMem(name, academicYear);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		CoursePostgresDao psqldao = new CoursePostgresDao();
		Course c = psqldao.getByNameNYear(name, academicYear);
		courseInMem.add(c);
		return c;
	}

	public void insertCourse(int teacherUid, String name, int cfu, int academicYear, boolean isActive)
			throws SQLException {

		CoursePostgresDao psqldao = new CoursePostgresDao();
		Course c = psqldao.insertCourse(teacherUid, name, cfu, academicYear, isActive);
		courseInMem.add(c);

	}
}
