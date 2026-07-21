package model;

/**
 * Interfaccia che generalizza il concetto di chiave primaria di un DB
 * 
 * @param <I> tipo dell'attributo identificativo della classe
 */
public interface Identifiable<I> {
	I getId();
}
