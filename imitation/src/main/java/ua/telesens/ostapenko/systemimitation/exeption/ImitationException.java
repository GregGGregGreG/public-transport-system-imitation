package ua.telesens.ostapenko.systemimitation.exeption;

/**
 * @author root
 * @since 18.01.16
 */
public class ImitationException extends RuntimeException {

    public ImitationException() {
        super();
    }

    public ImitationException(String message) {
        super(message);
    }

    public ImitationException(String message, Throwable cause) {
        super(message, cause);
    }


}
