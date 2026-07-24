package implementazioneDao.exception;

/***
 * Eccezione lanciata quando un'operazione di recupero di dati fallisce
 */
public class DataRetrievalException extends RuntimeException {
	public DataRetrievalException(String message) {
		super(message);
	}

	public DataRetrievalException(String message, Throwable cause) {
		super(message, cause);
	}
}
