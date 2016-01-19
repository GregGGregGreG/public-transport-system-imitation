package ua.telesens.ostapenko.systemimitation.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.XMLRouteListConverter;
import ua.telesens.ostapenko.systemimitation.exeption.SerialisationException;
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
public class XStreamXMLRouteListConverter implements XMLRouteListConverter {
    private static final String FORMAT = "%-40s%-20s";

    @Override
    public String toXML(RouteList routeList, String path) {
        log.info("----- Parse Route toXML XML -----");
        Objects.requireNonNull(routeList, "route list cannot be null");

        XStream xStream = new XStream();
        StringBuilder xml = new StringBuilder();
        try {
            File file = new File(path);
            xStream.processAnnotations(RouteList.class);
            xStream.processAnnotations(Route.class);
            xStream.setMode(XStream.NO_REFERENCES);
            xStream.registerConverter(new LocaleDateTimeConverter());
            xStream.registerConverter(new LocaleTimeConverter());

            //Remove class='list'
            xStream.aliasSystemAttribute(null, "class");

            log.info("\n" + xStream.toXML(routeList));
            xStream.toXML(routeList, new FileWriter(file));
            xml.append(Arrays.toString(Files.readAllBytes(file.toPath())));
            log.info(String.format(FORMAT, "Path toXML file", file.getAbsoluteFile()));

        } catch (IOException | XStreamException e) {
            throw new SerialisationException("Error convert route list to XMl", e);
        }
        return xml.toString();
    }

    @Override
    public RouteList fromXML(String xml) {
        log.info("Parse route list XML to object");
        Objects.requireNonNull(xml, "route list xml cannot be null");
        RouteList routeList;
        try {
            XStream xStream = new XStream();

            xStream.processAnnotations(RouteList.class);

            xStream.registerConverter(new LocaleDateTimeConverter());
            xStream.registerConverter(new LocaleTimeConverter());

            //Remove class='list'
            xStream.aliasSystemAttribute(null, "class");

            routeList = (RouteList) xStream.fromXML(new File(xml));
            log.debug(String.valueOf(routeList));
        } catch (XStreamException e) {
            throw new SerialisationException("Error parse route list XMl", e);
        }
        return routeList;
    }
}
