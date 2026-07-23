package controller;

import java.util.ArrayList;
import java.util.List;

import controller.cache.ClassroomCache;
import controller.exception.DatabaseException;
import controller.exception.UnauthorizedException;
import dao.ClassroomDao;
import implementazioneDao.ClassroomPostgresDao;
import implementazioneDao.entity.ClassroomEntity;
import implementazioneDao.exception.DataInsertionException;
import model.Classroom;

public class ClassroomService extends AbstractDaoService<Classroom, ClassroomEntity, String, ClassroomDao> {
	public ClassroomService() {
		super(new ClassroomPostgresDao());
	}

	@Override
	protected Classroom mapEntityToModel(ClassroomEntity e) {
		return new Classroom(e.getName());
	}

	@Override
	public Classroom getById(String id) {
		return mapEntityToModel(ClassroomCache.getInstance().getById(id));
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

	public List<String> getAllClassroomInfo() {
		List<ClassroomEntity> classroomsE = dao.getAllClassrooms();

		List<String> r = new ArrayList<>();

		for (ClassroomEntity c : classroomsE)
			r.add(c.getName());

		return r;
	}
}
