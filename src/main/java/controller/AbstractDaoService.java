package controller;

public abstract class AbstractDaoService<DAO> {
	protected final DAO dao;

	protected AbstractDaoService(DAO dao) {
		this.dao = dao;
	}

}
