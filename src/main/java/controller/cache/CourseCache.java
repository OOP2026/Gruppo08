package controller.cache;

import dao.CourseDao;
import implementazioneDao.CoursePostgresDao;
import implementazioneDao.entity.CourseEntity;

public class CourseCache extends AbstractCache<CourseEntity, Integer, CourseDao> {
	private CourseCache() {
		super(new CoursePostgresDao());
	}

	private static CourseCache instance;

	public static CourseCache getInstance() {
		if (instance == null)
			instance = new CourseCache();
		return instance;
	}
}
