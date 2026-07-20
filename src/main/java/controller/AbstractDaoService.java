package controller;

/**
 * Classe astratta di una classe servizio generica che si interfaccia ad una
 * classe in package dao
 *
 * @param <DAO> Il dao che viene utilizzato come dependency dalla classe
 *              servizio
 */
public abstract class AbstractDaoService<DAO> {
	protected final DAO dao;

	protected AbstractDaoService(DAO dao) {
		this.dao = dao;
	}

}
