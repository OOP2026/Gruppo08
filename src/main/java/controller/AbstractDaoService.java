package controller;

import implementazioneDao.entity.IdentifiableEntity;
import model.Identifiable;

/**
 * Classe astratta estesa da ogni classe servizio che si interfaccia al dao
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

	/***
	 * Metodo che mappa un'istanza del package implementazioneDao.entity a un'istanza del package model
	 * @param e istanza di entity
	 * @return istanza di model
	 */
	protected abstract M mapEntityToModel(E e);

	/***
	 * Metodo che restituisce un'istanza del model tramite il suo attributo identificativo
	 * specificato nell'interfaccia model.Identifiable
	 * @param id identificativo della classe
	 * @return istanza di model
	 */
	public abstract M getById(I id);
}
