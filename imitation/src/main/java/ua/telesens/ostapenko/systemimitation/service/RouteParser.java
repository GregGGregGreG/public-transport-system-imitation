package ua.telesens.ostapenko.systemimitation.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.model.internal.Route;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteList;
import ua.telesens.ostapenko.systemimitation.xstream.converters.LocaleDateTimeConverter;
import ua.telesens.ostapenko.systemimitation.xstream.converters.LocaleTimeConverter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

/**
 * @author root
 * @since 07.01.16
 */
@Slf4j
public class RouteParser {

    private RouteParser() {
    }

    private static final String FORMAT = "%-40s%-20s";

    public static RouteList fromXML(File file) {
        log.info(String.format(FORMAT, "Parse Route XML ", file.getAbsoluteFile()));


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

    public static RouteList fromXML(FileReader file) {
        log.info(String.format(FORMAT, "Parse Route XML ", file));


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
    public static File toXML(RouteList routeList) throws IOException {
        log.info("----- Parse Route to XML -----");

        XStream xStream = new XStream();

        String name = "data/rout_" + Instant.now() + ".xml";
        File file = new File(name);

        xStream.processAnnotations(RouteList.class);
        xStream.processAnnotations(Route.class);
        xStream.setMode(XStream.NO_REFERENCES);
        xStream.registerConverter(new LocaleDateTimeConverter());
        xStream.registerConverter(new LocaleTimeConverter());

        //Remove class='list'
        xStream.aliasSystemAttribute(null, "class");

        log.info("\n" + xStream.toXML(routeList));
        xStream.toXML(routeList, new FileWriter(file));
        log.info(String.format(FORMAT, "Path to file", file.getAbsoluteFile()));
        return file;
    }

    public static File toJSON(RouteList routeList) throws IOException {
        log.info("----- Parse Route object to JSON -----");

        XStream xStream = new XStream(new JsonHierarchicalStreamDriver());

        String name = "data/rout_" + Instant.now() + ".json";
        File file = new File(name);

        xStream.processAnnotations(RouteList.class);
        xStream.processAnnotations(Route.class);
        xStream.setMode(XStream.NO_REFERENCES);
        xStream.registerConverter(new LocaleDateTimeConverter());
        xStream.registerConverter(new LocaleTimeConverter());

        //Remove class='list'
        xStream.aliasSystemAttribute(null, "class");

        log.info("\n" + xStream.toXML(routeList));
        xStream.toXML(routeList, new FileWriter(file));
        log.info(String.format(FORMAT, "Path to file", file.getAbsoluteFile()));
        return file;
    }
}

