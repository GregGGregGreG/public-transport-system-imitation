package ua.telesens.ostapenko.systemimitation.api;

import java.io.IOException;

/**
 * @author root
 * @since 06.01.16
 */
public interface Report {

    Report show();

    Report toXML() throws IOException;
}
