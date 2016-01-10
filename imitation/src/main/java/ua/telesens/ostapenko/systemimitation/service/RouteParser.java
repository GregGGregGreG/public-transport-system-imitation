package ua.telesens.ostapenko.systemimitation.service;

import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.model.internal.Route;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteList;
import ua.telesens.ostapenko.systemimitation.xstream.converters.LocaleDateTimeConverter;
import ua.telesens.ostapenko.systemimitation.xstream.converters.LocaleTimeConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author root
 * @since 07.01.16
 */
@Slf4j
public class RouteParser {

    private static final String FORMAT = "%-40s%-20s";

    public RouteList fromXML(File file) {
        log.info("----- Route XML to Object -----");

        XStream xStream = new XStream();
        RouteList routeList;

        xStream.processAnnotations(RouteList.class);

        xStream.registerConverter(new LocaleDateTimeConverter());
        xStream.registerConverter(new LocaleTimeConverter());

        //Remove class='list'
        xStream.aliasSystemAttribute(null, "class");

        routeList = (RouteList) xStream.fromXML(file);
        return routeList;
    }

    // FIXME: 10.01.16 Fix structure from rules station and route
    public File toXML(RouteList routeList, File file) throws IOException {
        log.info("----- Route to XML -----");

        XStream xStream = new XStream();

        xStream.processAnnotations(RouteList.class);
        xStream.processAnnotations(Route.class);
        xStream.setMode(XStream.NO_REFERENCES);
        xStream.registerConverter(new LocaleDateTimeConverter());
        xStream.registerConverter(new LocaleTimeConverter());

        //Remove class='list'
        xStream.aliasSystemAttribute(null, "class");

        log.info("\n" + xStream.toXML(routeList));
        xStream.toXML(routeList, new FileWriter(file));
        log.info(String.format(FORMAT, "Path to file", file.getPath()));
        return file;
    }
}

