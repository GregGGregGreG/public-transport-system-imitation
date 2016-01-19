package ua.telesens.ostapenko.systemimitation.exeption;

/**
 * @author root
 * @since 18.01.16
 */
public class DataBaseException extends ImitationException {

    public DataBaseException(String message) {
        super(message);
    }

    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseException(Throwable cause) {
        super(cause);
    }
}
