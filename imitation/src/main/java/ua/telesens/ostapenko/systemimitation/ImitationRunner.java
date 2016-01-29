package ua.telesens.ostapenko.systemimitation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ua.telesens.ostapenko.systemimitation.api.ReportManager;
import ua.telesens.ostapenko.systemimitation.api.RouteListValidator;
import ua.telesens.ostapenko.systemimitation.dao.ReportDAO;
import ua.telesens.ostapenko.systemimitation.dao.impl.mysql.MySQlDAOFactory;
import ua.telesens.ostapenko.systemimitation.exeption.ImitationException;
import ua.telesens.ostapenko.systemimitation.model.internal.ImitationSource;
import ua.telesens.ostapenko.systemimitation.model.internal.Report;
import ua.telesens.ostapenko.systemimitation.service.*;

import java.util.Objects;

/**
 * @author root
 * @since 16.01.16
 */
@Slf4j
@Component
@PropertySource("classpath:application.properties")
public class ImitationRunner {

    @Autowired
    private MySQlDAOFactory mySQlDAOFactory;

    @Autowired
    private ReportManager reportManager;

    @Value("#{environment['path.properties.imitation.file']}")
    private String PROPERTY_PATH_IMITATION_SOURCE;

    public void run() {
        try {
            log.info("Initialize imitation");
            start(PROPERTY_PATH_IMITATION_SOURCE);
        } catch (ImitationException e) {
            log.error(String.valueOf(e.getClass()), e);
            log.info(e.getMessage());
        }
    }


    private void start(String path) {
        Objects.requireNonNull(path, "Path can't null");
        LoggerDB loggerDB = LoggerDB.of(mySQlDAOFactory);
        ReportDAO reportDAO = mySQlDAOFactory.getReportDAO();

        ImitationSource source = ImitationSourceParser.fromXML(path, new XStreamXMLImitationSourceConverter());
        RouteListValidator validator = new HibernateValidator();

        validator.validate(source);

        BusSystemImitation systemImitation = new BusSystemImitation(source);
        BusSystemImitationStatistic imitationStatistic = BusSystemImitationStatistic.of(systemImitation);

        imitationStatistic.setLogger(loggerDB);

        XStreamXMLReportConverter xmlConverter = new XStreamXMLReportConverter();

        Report report = imitationStatistic.execute();

        reportManager
                .setData(report)
                .toDB(reportDAO)
                .toXML(xmlConverter);
    }
}


