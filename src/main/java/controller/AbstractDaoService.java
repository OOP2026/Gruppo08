package controller;

/**
 * Classe astratta di una classe servizio generica che si interfaccia ad una
 * classe in package dao
 *
 * @param <D> Il dao che viene utilizzato come dependency dalla classe
 *            servizio
 */
public abstract class AbstractDaoService<D> {
	protected final D dao;

	protected AbstractDaoService(D dao) {
		this.dao = dao;
	}

}
