package ua.telesens.ostapenko.systemimitation.exeption;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Indicates a problem validating the route.
 *
 * @author root
 * @since 18.01.16
 */
public class ImitationSourceValidationException extends ImitationException {

    private static final String SEPARATOR = "\n-------------------------------";
    private List<String> stuff = new ArrayList<>();

    public ImitationSourceValidationException(String message) {
        super(message);
    }

    public ImitationSourceValidationException(String message, Throwable cause) {
        super(message, cause);
    }


    private Iterator keys() {
        return stuff.iterator();
    }

    public void add(String information) {
        stuff.add(information);
    }

    @Override
    public String getMessage() {
        StringBuilder result = new StringBuilder();
        if (super.getMessage() != null) {
            result.append(super.getMessage());
        }
        if (!result.toString().endsWith(SEPARATOR)) {
            result.append("\n---- Validation error information ----\n");
        }
        for (Iterator iterator = keys(); iterator.hasNext(); ) {
            String k = (String) iterator.next();
            result.append(k);
            if(iterator.hasNext()) {
                result.append("\n");
            }
        }
        result.append(SEPARATOR);
        return result.toString();
    }
}
