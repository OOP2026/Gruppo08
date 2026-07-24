package implementazioneDao.exception;

/***
 * Eccezione lanciata quando un'operazione di aggiornamento dei dati fallisce
 */
public class DataUpdateException extends RuntimeException {
	public DataUpdateException(String message) {
		super(message);
	}

	public DataUpdateException(String message, Throwable cause) {
		super(message, cause);
	}
}
