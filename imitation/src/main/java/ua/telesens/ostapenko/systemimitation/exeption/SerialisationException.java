package ua.telesens.ostapenko.systemimitation.exeption;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * @author root
 * @since 18.01.16
 */
public class SerialisationException extends ImitationException {

    private static final String SEPARATOR = "\n-------------------------------";
    private Map<String, String> stuff = new HashMap<>();

    public SerialisationException(String message) {
        super(message);
    }

    public SerialisationException(String message, Throwable cause) {
        super(message, cause);
    }

    public Iterator keys() {
        return stuff.keySet().iterator();
    }

    public String get(String errorKey) {
        return stuff.get(errorKey);
    }


    @Override
    public String getMessage() {
        StringBuilder result = new StringBuilder();
        if (super.getMessage() != null) {
            result.append(super.getMessage());
        }
        if (!stuff.isEmpty()) {
            if (!result.toString().endsWith(SEPARATOR)) {
                result.append("\n---- Parse error information ----");
            }
            for (Iterator iterator = keys(); iterator.hasNext(); ) {
                String k = (String) iterator.next();
                String v = get(k);
                result.append(String.format("\n%-25s : %-30s", k, v));
            }
            result.append(SEPARATOR);
        }
        return result.toString();
    }

    public void add(String name, String information) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(information);
        String key = name;
        int i = 0;
        while (stuff.containsKey(key)) {
            String value = stuff.get(key);
            if (information.equals(value))
                return;
            key = name + "[" + ++i + "]";
        }
        stuff.put(key, information);
    }
}
