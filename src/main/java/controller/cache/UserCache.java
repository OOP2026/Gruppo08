package controller.cache;

import dao.UserDao;
import implementazioneDao.UserPostgresDao;
import implementazioneDao.entity.UserEntity;

public class UserCache extends AbstractCache<UserEntity, Integer, UserDao> {
	private UserCache() {
		super(new UserPostgresDao());
	}

	private static UserCache instance;

	public static UserCache getInstance() {
		if (instance == null)
			instance = new UserCache();
		return instance;
	}
}
