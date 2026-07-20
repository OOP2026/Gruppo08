package daoImplementation;

import java.util.NoSuchElementException;

import model.Identifiable;

/**
 * Classe astratta base di tutte le classi in daoImplementation.
 * 
 * @param <ENTITY> L'entita' che il dao gestisce
 * @param <ID>     Il tipo della chiave primaria
 */
public abstract class AbstractSqldao<ENTITY extends Identifiable<ID>, ID> {
	public abstract ENTITY getById(ID id) throws NoSuchElementException;
}
