package ua.telesens.ostapenko.systemimitation.api;

import ua.telesens.ostapenko.systemimitation.dao.ReportDAO;
import ua.telesens.ostapenko.systemimitation.model.internal.Report;

import java.io.IOException;

/**
 * @author root
 * @since 06.01.16
 */
public interface ReportManager {

    ReportManager show();

    ReportManager toXML() throws IOException;

    ReportManager toJSON() throws IOException;

    ReportManager toDB(ReportDAO reportDAO) throws IOException;

    Report get();
}