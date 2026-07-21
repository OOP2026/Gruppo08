package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import dao.impl.AbstractSqldao;
import model.Identifiable;

/**
 * 
 * Classe astratta base di tutte le classi nel package dao
 *
 * @param <E> la classe che gestisce il dao
 * @param <D> la classe del package dao.impl che viene utilizzata
 *            per ricavare i dati persistenti
 * @param <I> il tipo della chiave primaria dell'E
 */
public abstract class AbstractDao<E extends Identifiable<I>, D extends AbstractSqldao<E, I>, I> {
	protected final D sqldao;
	protected List<E> inMem;

	protected AbstractDao(D sqldao) {
		this.sqldao = sqldao;
		this.inMem = new ArrayList<>();
	}

	/**
	 * @param id chiave primaria dell'entita'
	 * @return true se id e' presente in memory, false nel caso contrario
	 */
	protected boolean isIdInMem(I id) {
		for (E e : inMem) {
			if (e.getId().equals(id))
				return true;
		}
		return false;
	}

	/**
	 * @param id chiave primaria dell'entita'
	 * @return E che ha un match con l'id
	 * @throws NoSuchElementException se l'istanza non e' stata trovata
	 */
	protected E getByIdInMem(I id) throws NoSuchElementException {
		for (E e : inMem) {
			if (e.getId().equals(id))
				return e;
		}

		throw new NoSuchElementException(id + " id not found in mem");
	}

	/**
	 * Restituisce una E dall'I cercando prima in memory e,
	 * successivamente, se il dato non e' presente, nel db.
	 *
	 * @throws NoSuchElementException se il dato non e' presente
	 */
	public E getById(I id) throws NoSuchElementException {
		if (isIdInMem(id)) {
			try {
				return getByIdInMem(id);
			} catch (NoSuchElementException e) {
				throw new IllegalStateException("memory corruption for I: " + id, e);
			}
		}

		E e = sqldao.getById(id);
		inMem.add(e);
		return e;
	}
}
