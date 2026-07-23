package dao;

import implementazioneDao.entity.IdentifiableEntity;

/**
 * interfaccia di tutte le classi in implementazioneDao.
 * 
 * @param <E> L'entita' che il dao gestisce
 * @param <I> Il tipo della chiave primaria
 */
public interface GenericDao<E extends IdentifiableEntity<I>, I> {
	public E getById(I id);
}
