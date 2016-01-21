package ua.telesens.ostapenko.systemimitation.api;

import ua.telesens.ostapenko.systemimitation.exeption.SerialisationException;
import ua.telesens.ostapenko.systemimitation.model.internal.Report;

/**
 * @author root
 * @since 18.01.16
 */
public interface XMLReportConverter {

    void toXML(Report report, String path) throws SerialisationException;
}
