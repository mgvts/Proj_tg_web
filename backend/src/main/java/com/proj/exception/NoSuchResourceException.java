package  com.proj.exception;

public class NoSuchResourceException extends RuntimeException {
    public NoSuchResourceException() {
        super();
    }

    public NoSuchResourceException(String message) {
        super(message);
    }

    public NoSuchResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchResourceException(Throwable cause) {
        super(cause);
    }
}
