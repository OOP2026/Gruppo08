package controller.cache;

import dao.ChangeOfDateReqDao;
import implementazioneDao.ChangeOfDateReqPostgresDao;
import implementazioneDao.entity.ChangeOfDateReqEntity;

public class ChangeOfDateReqCache extends AbstractCache<ChangeOfDateReqEntity, Integer, ChangeOfDateReqDao> {
	private ChangeOfDateReqCache() {
		super(new ChangeOfDateReqPostgresDao());
	}

	private static ChangeOfDateReqCache instance;

	public static ChangeOfDateReqCache getInstance() {
		if (instance == null)
			instance = new ChangeOfDateReqCache();
		return instance;
	}
}
