package ua.telesens.ostapenko.systemimitation.exeption;

/**
 * @author root
 * @since 18.01.16
 */
public class SerialisationException extends ImitationException {
    public SerialisationException(String message) {
        super(message);
    }

    public SerialisationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerialisationException(Throwable cause) {
        super(cause);
    }

}
