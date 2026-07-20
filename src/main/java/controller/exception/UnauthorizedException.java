package controller.exception;

/**
 * Eccezione runtime lanciata quando viene eseguito un metodo da un User in
 * session che non ha i permessi necessari
 */
public class UnauthorizedException extends RuntimeException {
	public UnauthorizedException(String message) {
		super(message);
	}

	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	}
}
