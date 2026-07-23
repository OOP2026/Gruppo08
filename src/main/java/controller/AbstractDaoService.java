package controller;

import implementazioneDao.entity.IdentifiableEntity;
import model.Identifiable;

/**
 * Classe astratta di una classe servizio generica che si interfaccia ad una
 * classe in package dao
 *
 * @param <M> classe del model associata al servizio
 * @param <E> classe del daoImplementation.entity associata al servizio
 * @param <I> tipo dell'attributo chiave
 * @param <D> Il dao che viene utilizzato come dependency dalla classe
 *            servizio
 */
public abstract class AbstractDaoService<M extends Identifiable<I>, E extends IdentifiableEntity<I>, I, D> {
	protected final D dao;

	protected AbstractDaoService(D dao) {
		this.dao = dao;
	}

	protected abstract M mapEntityToModel(E e);

	public abstract M getById(I id);
}
