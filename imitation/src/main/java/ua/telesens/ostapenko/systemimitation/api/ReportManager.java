package ua.telesens.ostapenko.systemimitation.api;

import ua.telesens.ostapenko.systemimitation.dao.ReportDAO;
import ua.telesens.ostapenko.systemimitation.model.internal.Report;

/**
 * @author root
 * @since 06.01.16
 */
public interface ReportManager {

    ReportManager show();

    ReportManager toXML(XMLReportConverter converter);

    ReportManager toJSON();

    ReportManager toDB(ReportDAO reportDAO) ;

    Report get();
}
