package ua.telesens.ostapenko.systemimitation.exeption;

/**
 * @author root
 * @since 21.01.16
 */
public class ImitationSourceNotFoundException extends ImitationException {

    public ImitationSourceNotFoundException() {
    }

    public ImitationSourceNotFoundException(String message) {
        super(message);
    }

    public ImitationSourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
