package controller;

import java.sql.SQLException;

import dao.CourseDao;

public class CourseService {
	public void makeCourse(int teacherUid, String name, int cfu, int academicYear, boolean isActive)
			throws RuntimeException {
		if (!SessionManager.getInstance().isCoordinator())
			throw new RuntimeException("This operation is restricted to coordinators only");

		CourseDao cdao = CourseDao.getInstance();
		try {
			cdao.insertCourse(teacherUid, name, cfu, academicYear, isActive);
		} catch (SQLException e) {
			throw new RuntimeException("unable to insert course with name " + name);
		}
	}

}
