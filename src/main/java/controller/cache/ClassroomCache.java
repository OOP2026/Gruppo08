package controller.cache;

import dao.ClassroomDao;
import implementazioneDao.ClassroomPostgresDao;
import implementazioneDao.entity.ClassroomEntity;

public class ClassroomCache extends AbstractCache<ClassroomEntity, String, ClassroomDao> {
	private ClassroomCache() {
		super(new ClassroomPostgresDao());
	}

	private static ClassroomCache instance;

	public static ClassroomCache getInstance() {
		if (instance == null)
			instance = new ClassroomCache();
		return instance;
	}
}
