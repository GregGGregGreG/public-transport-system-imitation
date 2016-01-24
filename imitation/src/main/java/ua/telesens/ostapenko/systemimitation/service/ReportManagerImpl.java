package ua.telesens.ostapenko.systemimitation.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import ua.telesens.ostapenko.systemimitation.api.ReportManager;
import ua.telesens.ostapenko.systemimitation.api.XMLReportConverter;
import ua.telesens.ostapenko.systemimitation.dao.ReportDAO;
import ua.telesens.ostapenko.systemimitation.model.internal.Report;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author root
 * @since 11.01.16
 */
@Slf4j
@Getter
@RequiredArgsConstructor(staticName = "of")
@ToString
@PropertySource("classpath:application.properties")
public class ReportManagerImpl implements ReportManager {

    private static final String XML = "xml";

    @Value("#{environment['name.report.file']}")
    private String PROPERTY_NAME_REPORT_FILE;

    @Value("#{environment['path.report.folder']}")
    private String PROPERTY_PATH_REPORT_FOLDER;

    private static final String FORMAT = "%-40s%-20s";
    private final Report data;

    @Override
    public ReportManager show() {
        log.info("----- Summary Statistic -----");
        log.info(String.format(FORMAT, "Start imitation", data.getStartImitation()));
        log.info(String.format(FORMAT, "Stop imitation", data.getEndImitation()));
        log.info(String.format(FORMAT, "Duration imitation", data.getDuration()));
        log.info(String.format(FORMAT, "Count rout", data.getRoutes()));
        log.info(String.format(FORMAT, "Count bus", data.getBuses()));
        log.info(String.format(FORMAT, "Count station ", data.getStations()));
        log.info(String.format(FORMAT, "Avg capacity bus", data.getBusAvgCapacity()));
        log.info(String.format(FORMAT, "Number trips", data.getNumberTrips()));
        log.info(String.format(FORMAT, "All transported passenger", data.getTransportedPassenger()));
        log.info(String.format(FORMAT, "Occupancy Percentage of buses ", data.getBusesPercentageOccupancy()));
        log.info(String.format(FORMAT, "Lazy passenger", data.getLazyPassenger()));
        return this;
    }

    @Override
    public ReportManager toXML(XMLReportConverter converter) {
        Objects.requireNonNull(converter, "report cannot be null");
        String path = PROPERTY_PATH_REPORT_FOLDER + "/"
                + PROPERTY_NAME_REPORT_FILE + "_" +
                LocalDateTime.now() + "." + XML;
        converter.toXML(data, path);
        return this;
    }

    @Override
    public ReportManager toJSON() {
//        log.info("Export report toXML JSON");
//
//        XStream xStream = new XStream(new JsonHierarchicalStreamDriver());
//        String name = "imitation/report/report_" + Instant.now() + ".json";
//        File file = new File(name);
//
//        xStream.processAnnotations(Report.class);
//        xStream.setMode(XStream.NO_REFERENCES);
//        xStream.registerConverter(new LocaleDateTimeConverter());
//        xStream.registerConverter(new LocaleTimeConverter());
//        xStream.registerConverter(new DurationConverter());
//        xStream.registerConverter(new NamedMapConverter(xStream.getMapper(),
//                "val",
//                "dayType", DayType.class,
//                "time", LocalTime.class));
//        log.info("\n" + xStream.toXML(data));
//        xStream.toXML(data, new FileWriter(file));
//        log.info(String.format(FORMAT, "Path toXML JSON file report", file.getAbsolutePath()));
        return this;
    }

    @Override
    public ReportManager toDB(ReportDAO dao) {
        log.info("Export report toXML DB");
        dao.insertReport(data);
        return this;
    }

    @Override
    public Report get() {
        return data;
    }
}
