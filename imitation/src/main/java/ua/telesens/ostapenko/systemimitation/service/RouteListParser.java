package ua.telesens.ostapenko.systemimitation.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;
import ua.telesens.ostapenko.systemimitation.api.XMLRouteListConverter;
import ua.telesens.ostapenko.systemimitation.model.internal.Route;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteList;
import ua.telesens.ostapenko.systemimitation.xstream.converters.LocaleDateTimeConverter;
import ua.telesens.ostapenko.systemimitation.xstream.converters.LocaleTimeConverter;

import java.io.*;
import java.time.Instant;

/**
 * @author root
 * @since 07.01.16
 */
@Slf4j
public class RouteListParser {

    private RouteListParser() {
    }

    private static final String FORMAT = "%-40s%-20s";

    public static RouteList fromXML(String xml, XMLRouteListConverter converter) {
        return converter.fromXML(xml);
    }

    public static String toXML(RouteList routeList, XMLRouteListConverter converter) {
        String path = "data/rout_" + Instant.now() + ".json";
        return converter.toXML(routeList, path);
    }

    public static RouteList fromXML(String xml) throws FileNotFoundException {
        log.info("Parse Route XML");
        File file = new File(xml);
        FileReader fileReader = new FileReader(file);
        log.debug(String.format(FORMAT, "Parse Route XML ", file.getAbsoluteFile()));

        XStream xStream = new XStream();
        RouteList routeList;

        xStream.processAnnotations(RouteList.class);

        xStream.registerConverter(new LocaleDateTimeConverter());
        xStream.registerConverter(new LocaleTimeConverter());

        //Remove class='list'
        xStream.aliasSystemAttribute(null, "class");

        routeList = (RouteList) xStream.fromXML(file);
        log.debug(String.valueOf(routeList));
        return routeList;
    }

    public static RouteList fromXML(FileReader file) throws SAXException {
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
        log.info("----- Parse Route toXML XML -----");

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
        log.info(String.format(FORMAT, "Path toXML file", file.getAbsoluteFile()));
        return file;
    }

    public static File toJSON(RouteList routeList) throws IOException {
        log.info("----- Parse Route object toXML JSON -----");

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
        log.info(String.format(FORMAT, "Path toXML file", file.getAbsoluteFile()));
        return file;
    }
}

