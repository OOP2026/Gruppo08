package controller.cache;

import dao.LectureDao;
import implementazioneDao.LecturePostgresDao;
import implementazioneDao.entity.LectureEntity;

public class LectureCache extends AbstractCache<LectureEntity, Integer, LectureDao> {
	private LectureCache() {
		super(new LecturePostgresDao());
	}

	private static LectureCache instance;

	public static LectureCache getInstance() {
		if (instance == null)
			instance = new LectureCache();
		return instance;
	}
}
