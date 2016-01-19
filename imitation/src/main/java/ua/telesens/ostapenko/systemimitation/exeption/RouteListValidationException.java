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
public class RouteListValidationException extends ImitationException {

    private static final String SEPARATOR = "\n-------------------------------";
    private List<String> stuff = new ArrayList<>();

    public RouteListValidationException(String message) {
        super(message);
    }

    public RouteListValidationException(String message, Throwable cause) {
        super(message, cause);
    }


    public RouteListValidationException(Throwable cause) {
        super(cause);
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
            result.append("\n---- Debugging information ----\n");
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
