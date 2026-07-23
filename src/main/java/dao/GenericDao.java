package dao;

import dao.dto.IdentifiableDTO;

/**
 * interfaccia di tutte le classi in implementazioneDao.
 * 
 * @param <E> L'entita' che il dao gestisce
 * @param <I> Il tipo della chiave primaria
 */
public interface GenericDao<E extends IdentifiableDTO<I>, I> {
	public E getById(I id);
}
