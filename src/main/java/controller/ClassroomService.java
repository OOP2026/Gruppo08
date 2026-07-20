package controller;

import java.sql.SQLException;

import controller.exception.DatabaseException;
import controller.exception.UnauthorizedException;
import dao.ClassroomDao;

public class ClassroomService {
	private final ClassroomDao cdao = ClassroomDao.getInstance();

	/**
	 * @throws UnauthorizedException se l'utente non e' coordinatore
	 * @throws DatabaseException     se il db fallisce
	 */
	public void makeClassroom(String name) {
		if (!SessionManager.getInstance().isCoordinator())
			throw new UnauthorizedException("This operation is restricted to coordinators only");

		try {
			cdao.insertClassroom(name);
		} catch (SQLException e) {
			throw new DatabaseException("unable to insert classroom with name " + name, e);
		}
	}
}
