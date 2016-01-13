package ua.telesens.ostapenko.systemimitation.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.NamedMapConverter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.ReportManager;
import ua.telesens.ostapenko.systemimitation.dao.ReportDAO;
import ua.telesens.ostapenko.systemimitation.model.internal.DayType;
import ua.telesens.ostapenko.systemimitation.model.internal.Report;
import ua.telesens.ostapenko.systemimitation.xstream.converters.DurationConverter;
import ua.telesens.ostapenko.systemimitation.xstream.converters.LocaleDateTimeConverter;
import ua.telesens.ostapenko.systemimitation.xstream.converters.LocaleTimeConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalTime;

/**
 * @author root
 * @since 11.01.16
 */
@Slf4j
@Getter
@RequiredArgsConstructor(staticName = "of")
@ToString
public class ReportManagerImpl implements ReportManager {

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
    public ReportManager toXML() throws IOException {
        log.info("Export report to XML");

        XStream xStream = new XStream();
        String name = "imitation/report/report_" + Instant.now() + ".xml";
        File file = new File(name);

        xStream.processAnnotations(Report.class);
        xStream.setMode(XStream.NO_REFERENCES);
        xStream.registerConverter(new LocaleDateTimeConverter());
        xStream.registerConverter(new LocaleTimeConverter());
        xStream.registerConverter(new DurationConverter());
        xStream.registerConverter(new NamedMapConverter(xStream.getMapper(),
                "val",
                "dayType", DayType.class,
                "time", LocalTime.class));
        log.info("\n" + xStream.toXML(data));
        xStream.toXML(data, new FileWriter(file));
        log.info(String.format(FORMAT, "Path to XML file report", file.getAbsolutePath()));
        return this;
    }

    @Override
    public ReportManager toJSON() throws IOException {
        log.info("Export report to JSON");

        XStream xStream = new XStream(new JsonHierarchicalStreamDriver());
        String name = "imitation/report/report_" + Instant.now() + ".json";
        File file = new File(name);

        xStream.processAnnotations(Report.class);
        xStream.setMode(XStream.NO_REFERENCES);
        xStream.registerConverter(new LocaleDateTimeConverter());
        xStream.registerConverter(new LocaleTimeConverter());
        xStream.registerConverter(new DurationConverter());
        xStream.registerConverter(new NamedMapConverter(xStream.getMapper(),
                "val",
                "dayType", DayType.class,
                "time", LocalTime.class));
        log.info("\n" + xStream.toXML(data));
        xStream.toXML(data, new FileWriter(file));
        log.info(String.format(FORMAT, "Path to JSON file report", file.getAbsolutePath()));
        return this;
    }

    @Override
    public ReportManager toDB(ReportDAO dao) throws IOException {
        log.info("Export report to DB");
        dao.insertReport(data);
        return this;
    }

    @Override
    public Report get() {
        return data;
    }
}
