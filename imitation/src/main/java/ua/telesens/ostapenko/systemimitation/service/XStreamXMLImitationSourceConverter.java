package ua.telesens.ostapenko.systemimitation.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.converters.ConversionException;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.XMLImitationSourceConverter;
import ua.telesens.ostapenko.systemimitation.exeption.SerialisationException;
import ua.telesens.ostapenko.systemimitation.model.internal.ImitationSource;
import ua.telesens.ostapenko.systemimitation.model.internal.Route;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteList;
import ua.telesens.ostapenko.systemimitation.xstream.converters.LocaleDateTimeConverter;
import ua.telesens.ostapenko.systemimitation.xstream.converters.LocaleTimeConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author root
 * @since 18.01.16
 */
@Slf4j
public class XStreamXMLImitationSourceConverter implements XMLImitationSourceConverter {
    private static final String FORMAT = "%-40s%-20s";

    @Override
    public String toXML(ImitationSource source, String path) {
        log.info("----- Parse imitation source toXML XML -----");
        Objects.requireNonNull(source, "imitation source cannot be null");
        Objects.requireNonNull(path, "path cannot be null");

        XStream xStream = new XStream();
        StringBuilder xml = new StringBuilder();
        try {
            File file = new File(path);
            xStream.processAnnotations(ImitationSource.class);
            xStream.processAnnotations(RouteList.class);
            xStream.processAnnotations(Route.class);
            xStream.setMode(XStream.NO_REFERENCES);
            xStream.registerConverter(new LocaleDateTimeConverter());
            xStream.registerConverter(new LocaleTimeConverter());

            //Remove class='list'
            xStream.aliasSystemAttribute(null, "class");

            log.info("\n" + xStream.toXML(source));
            xStream.toXML(source, new FileWriter(file));
            xml.append(Arrays.toString(Files.readAllBytes(file.toPath())));
            log.info(String.format(FORMAT, "Path toXML file", file.getAbsoluteFile()));

        } catch (IOException | XStreamException e) {
            throw new SerialisationException("Error convert imitation source to XMl", e);
        }
        return xml.toString();
    }

    @Override
    public ImitationSource fromXML(String xml) {
        log.info("Parse imitation source XML to object");
        Objects.requireNonNull(xml, "imitation source xml cannot be null");
        ImitationSource source = null;
        try {
            XStream xStream = new XStream();

            xStream.processAnnotations(ImitationSource.class);
            xStream.registerConverter(new LocaleDateTimeConverter());
            xStream.registerConverter(new LocaleTimeConverter());

            source = (ImitationSource) xStream.fromXML(xml);
            log.debug(String.valueOf(source));
        } catch (ConversionException e) {
            SerialisationException exception =
                    new SerialisationException("error parse imitation source XMl", e);
            exception.add("error", e.getShortMessage());
            exception.add("path", e.get("path"));
            exception.add("line number", e.get("line number"));
            throw exception;
        } catch (XStreamException e) {
            throw new SerialisationException("error parse imitation source XMl", e);
        }
        return source;
    }

}
