package ua.telesens.ostapenko.systemimitation.exeption;

/**
 * @author root
 * @since 18.01.16
 */
public class ImitationException extends RuntimeException {


    private Throwable cause;

    public ImitationException() {
        this("", null);

    }

    public ImitationException(String message) {
        this(message, null);

    }

    public ImitationException(String message, Throwable cause) {
        super(message + (cause == null ? "" : " : " + cause.getMessage()));
        this.cause = cause;

    }

    public ImitationException(Throwable cause) {
        this("", cause);

    }

    @Override
    public synchronized Throwable getCause() {
        return cause;
    }
}
