package controller;

import controller.exception.DatabaseException;
import controller.exception.UnauthorizedException;
import dao.ClassroomDao;
import implementazioneDao.exception.DataInsertionException;

public class ClassroomService extends AbstractDaoService<ClassroomDao> {
	public ClassroomService() {
		super(ClassroomDao.getInstance());
	}

	/**
	 * @throws UnauthorizedException se l'utente non e' coordinatore
	 * @throws DatabaseException     se il db fallisce
	 */
	public void makeClassroom(String name) {
		if (!SessionManager.getInstance().isCoordinator())
			throw new UnauthorizedException("This operation is restricted to coordinators only");

		try {
			dao.insertClassroom(name);
		} catch (DataInsertionException e) {
			throw new DatabaseException("unable to insert classroom with name " + name, e);
		}
	}
}
