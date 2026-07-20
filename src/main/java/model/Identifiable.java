package model;

/**
 * Interfaccia che generalizza il concetto di chiave primaria di un DB
 * 
 * @param <ID> tipo dell'attributo identificativo della classe
 */
public interface Identifiable<ID> {
	ID getId();
}
