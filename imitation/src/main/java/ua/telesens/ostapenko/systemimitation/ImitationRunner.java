package ua.telesens.ostapenko.systemimitation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.telesens.ostapenko.systemimitation.api.RouteListValidator;
import ua.telesens.ostapenko.systemimitation.dao.ReportDAO;
import ua.telesens.ostapenko.systemimitation.dao.impl.mysql.MySQlDAOFactory;
import ua.telesens.ostapenko.systemimitation.exeption.ImitationException;
import ua.telesens.ostapenko.systemimitation.model.internal.ImitationSource;
import ua.telesens.ostapenko.systemimitation.service.*;

/**
 * @author root
 * @since 16.01.16
 */
@Slf4j
@Component
public class ImitationRunner {

    @Autowired
    private MySQlDAOFactory mySQlDAOFactory;

    public void run(String path) {
        try {
            log.info("Initialize imitation");
            start(path);
        } catch (ImitationException e) {
            log.error(String.valueOf(e.getClass()), e);
            log.info(e.getMessage());
        }
    }

    private void start(String path) {
        LoggerDB loggerDB = LoggerDB.of(mySQlDAOFactory);
        ReportDAO reportDAO = mySQlDAOFactory.getReportDAO();

        ImitationSource source = ImitationSourceParser.fromXML(path, new XStreamXMLImitationSourceConverter());
        RouteListValidator validator = new HibernateValidator();

        validator.validate(source);

        BusSystemImitation systemImitation = new BusSystemImitation(source);
        BusSystemImitationStatistic imitationStatistic = BusSystemImitationStatistic.of(systemImitation);

        imitationStatistic.setLogger(loggerDB);

        XStreamXMLReportConverter xmlConverter = new XStreamXMLReportConverter();

        imitationStatistic.execute()
                .show()
                .toDB(reportDAO)
                .toJSON()
                .toXML(xmlConverter);
    }
}


