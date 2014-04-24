package dk.langli.piteraq;

public class CryptographicException extends Exception {
	private static final long serialVersionUID = 2109382329764532039L;

	public CryptographicException() {
		super();
	}

	public CryptographicException(String message, Throwable cause) {
		super(message, cause);
	}

	public CryptographicException(String message) {
		super(message);
	}

	public CryptographicException(Throwable cause) {
		super(cause);
	}
}
