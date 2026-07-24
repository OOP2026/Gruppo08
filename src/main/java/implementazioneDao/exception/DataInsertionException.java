package implementazioneDao.exception;

/***
 * Eccezione lanciata quando un'operazione di inserzione del database fallisce
 */
public class DataInsertionException extends RuntimeException {
	public DataInsertionException(String message) {
		super(message);
	}

	public DataInsertionException(String message, Throwable cause) {
		super(message, cause);
	}
}
