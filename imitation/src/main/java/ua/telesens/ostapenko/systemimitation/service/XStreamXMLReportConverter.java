package ua.telesens.ostapenko.systemimitation.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.converters.extended.NamedMapConverter;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.XMLReportConverter;
import ua.telesens.ostapenko.systemimitation.exeption.SerialisationException;
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
import java.util.Objects;

/**
 * @author root
 * @since 18.01.16
 */
@Slf4j
public class XStreamXMLReportConverter implements XMLReportConverter {

    private static final String FORMAT = "%-40s%-20s";

    @Override
    public void toXML(Report report) throws SerialisationException {
        log.info("Export report toXML XML");
        Objects.requireNonNull(report, "report cannot be null");
        try {
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
            log.info("\n" + xStream.toXML(report));

            xStream.toXML(report, new FileWriter(file));
            log.info(String.format(FORMAT, "Path toXML XML file report", file.getAbsolutePath()));

        } catch (IOException | XStreamException e) {
            throw new SerialisationException("Error save report to XMl", e);
        }
    }
}
