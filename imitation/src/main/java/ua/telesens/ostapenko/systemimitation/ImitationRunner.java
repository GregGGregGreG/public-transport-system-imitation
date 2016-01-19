package ua.telesens.ostapenko.systemimitation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.telesens.ostapenko.systemimitation.api.RouteListValidator;
import ua.telesens.ostapenko.systemimitation.dao.ReportDAO;
import ua.telesens.ostapenko.systemimitation.dao.impl.mysql.MySQlDAOFactory;
import ua.telesens.ostapenko.systemimitation.exeption.ImitationException;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteList;
import ua.telesens.ostapenko.systemimitation.service.*;

import java.time.LocalDateTime;

/**
 * @author root
 * @since 16.01.16
 */
@Slf4j
@Component
public class ImitationRunner {

    @Autowired
    MySQlDAOFactory mySQlDAOFactory;

    public void run(String xml, LocalDateTime start, LocalDateTime end) {
        try {
            log.info("Initialize imitation");
            start(xml, start, end);
        } catch (ImitationException e) {
            log.debug(String.valueOf(e.getClass()), e);
            log.error(e.getMessage());
        }
    }

    private void start(String xml, LocalDateTime start, LocalDateTime end) {
        LoggerDB loggerDB = LoggerDB.of(mySQlDAOFactory);
        ReportDAO reportDAO = mySQlDAOFactory.getReportDAO();

        RouteList routeList = RouteListParser.fromXML(xml, new XStreamXMLRouteListConverter());
        RouteListValidator validator = new HibernateValidator();

        validator.validate(routeList);

        BusSystemImitation systemImitation = new BusSystemImitation(routeList, start, end);
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


