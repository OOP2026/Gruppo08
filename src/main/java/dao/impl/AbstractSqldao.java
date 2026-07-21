package dao.impl;

import java.util.NoSuchElementException;

import model.Identifiable;

/**
 * Classe astratta base di tutte le classi in dao.impl.
 * 
 * @param <E> L'entita' che il dao gestisce
 * @param <I> Il tipo della chiave primaria
 */
public abstract class AbstractSqldao<E extends Identifiable<I>, I> {
	public abstract E getById(I id) throws NoSuchElementException;
}
