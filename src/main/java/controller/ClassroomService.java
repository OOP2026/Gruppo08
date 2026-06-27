package controller;

import java.sql.SQLException;

import dao.ClassroomDao;

public class ClassroomService {
	public void makeClassroom(String name) throws RuntimeException {
		if (!SessionManager.getInstance().isCoordinator())
			throw new RuntimeException("This operation is restricted to coordinators only");

		ClassroomDao cdao = ClassroomDao.getInstance();
		try {
			cdao.insertClassroom(name);
		} catch (SQLException e) {
			throw new RuntimeException("unable to insert classroom with name " + name);
		}
	}
}
