package ua.telesens.ostapenko;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import ua.telesens.ostapenko.conf.PersistenceContext;
import ua.telesens.ostapenko.systemimitation.dao.ReportDAO;
import ua.telesens.ostapenko.systemimitation.dao.impl.mysql.MySQlDAOFactory;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteList;
import ua.telesens.ostapenko.systemimitation.service.BusSystemImitation;
import ua.telesens.ostapenko.systemimitation.service.BusSystemImitationStatistic;
import ua.telesens.ostapenko.systemimitation.service.LoggerDB;
import ua.telesens.ostapenko.systemimitation.service.RouteParser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * @author root
 * @since 10.01.16
 */
@Configuration
@ComponentScan(basePackages = {"ua.telesens.ostapenko"})
@Import(PersistenceContext.class)
public class Application {
    private static final LocalDateTime STARTING = LocalDateTime.of(2015, Month.NOVEMBER, 12, 6, 0);
    private static final LocalDateTime END = LocalDateTime.of(2015, Month.NOVEMBER, 15, 23, 50);

    @Bean
    public BusSystemImitationStatistic systemImitation() throws IOException {
        return BusSystemImitationStatistic.of(new BusSystemImitation(
                getSource(),
                STARTING,
                END));
    }

    private RouteList getSource() {
        return RouteParser.fromXML(new File("imitation/data/rout_2016-01-12T15:47:23.017Z.xml"));
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        MySQlDAOFactory mySQlDAOFactory = context.getBean(MySQlDAOFactory.class);
        LoggerDB loggerDB = LoggerDB.of(mySQlDAOFactory);
        ReportDAO reportDAO = mySQlDAOFactory.getReportDAO();
        BusSystemImitationStatistic busSystemImitation = context.getBean(BusSystemImitationStatistic.class);
        busSystemImitation.setLogger(loggerDB);
        busSystemImitation.execute()
                .show()
                .toDB(reportDAO)
                .toJSON()
                .toXML();
    }
}