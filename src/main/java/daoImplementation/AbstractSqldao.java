package daoImplementation;

import java.util.NoSuchElementException;

import databaseConnection.DbConnection;
import model.Identifiable;

public abstract class AbstractSqldao<ENTITY extends Identifiable<ID>, ID> {
	protected DbConnection dbc = DbConnection.getInstance();

	public abstract ENTITY getById(ID id) throws NoSuchElementException;
}
