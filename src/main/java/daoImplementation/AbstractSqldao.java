package daoImplementation;

import java.util.NoSuchElementException;

import model.Identifiable;

public abstract class AbstractSqldao<ENTITY extends Identifiable<ID>, ID> {
	public abstract ENTITY getById(ID id) throws NoSuchElementException;
}
