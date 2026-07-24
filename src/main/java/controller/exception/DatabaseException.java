package controller.exception;

/***
 * Eccezione lanciata per generalizzare gli errori delle classi nel package dao.
 */
public class DatabaseException extends RuntimeException {
	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}
}
