package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import daoImplementation.AbstractSqldao;
import model.Identifiable;

/**
 * 
 * Classe astratta base di tutte le classi nel package dao
 *
 * @param <ENTITY> la classe che gestisce il dao
 * @param <SQLDAO> la classe del package daoImplementation che viene utilizzata
 *                 per ricavare i dati persistenti
 * @param <ID>     il tipo della chiave primaria dell'ENTITY
 */
public abstract class AbstractDao<ENTITY extends Identifiable<ID>, SQLDAO extends AbstractSqldao<ENTITY, ID>, ID> {
	protected final SQLDAO sqldao;
	protected List<ENTITY> inMem;

	protected AbstractDao(SQLDAO sqldao) {
		this.sqldao = sqldao;
		this.inMem = new ArrayList<>();
	}

	/**
	 * @param id chiave primaria dell'entita'
	 * @return true se id e' presente in memory, false nel caso contrario
	 */
	protected boolean isIdInMem(ID id) {
		for (ENTITY e : inMem) {
			if (e.getId().equals(id))
				return true;
		}
		return false;
	}

	/**
	 * @param id chiave primaria dell'entita'
	 * @return ENTITY che ha un match con l'id
	 * @throws NoSuchElementException se l'istanza non e' stata trovata
	 */
	protected ENTITY getByIdInMem(ID id) throws NoSuchElementException {
		for (ENTITY e : inMem) {
			if (e.getId().equals(id))
				return e;
		}

		throw new NoSuchElementException(id + " id not found in mem");
	}

	/**
	 * Restituisce una ENTITY dall'ID cercando prima in memory e,
	 * successivamente, se il dato non e' presente, nel db.
	 *
	 * @throws NoSuchElementException se il dato non e' presente
	 */
	public ENTITY getById(ID id) throws NoSuchElementException {
		if (isIdInMem(id)) {
			try {
				return getByIdInMem(id);
			} catch (NoSuchElementException e) {
				throw new IllegalStateException("memory corruption for ID: " + id, e);
			}
		}

		ENTITY e = sqldao.getById(id);
		inMem.add(e);
		return e;
	}
}
