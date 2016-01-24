package ua.telesens.ostapenko.systemimitation.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.XMLImitationSourceConverter;
import ua.telesens.ostapenko.systemimitation.exeption.ImitationSourceNotFoundException;
import ua.telesens.ostapenko.systemimitation.model.internal.ImitationSource;
import ua.telesens.ostapenko.systemimitation.model.internal.Route;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteList;
import ua.telesens.ostapenko.systemimitation.xstream.converters.LocaleDateTimeConverter;
import ua.telesens.ostapenko.systemimitation.xstream.converters.LocaleTimeConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

/**
 * @author root
 * @since 07.01.16
 */
@Slf4j
public final class ImitationSourceParser {

    private static final String IMITATION_SOURCE_NOT_FOUND = "Imitation source not found";
    private static final String FORMAT = "%-40s%-20s";

    private ImitationSourceParser() {
    }

    public static ImitationSource fromXML(String path, XMLImitationSourceConverter converter) {
        String xml;
        try {
            log.info("Read imitation source file");
            xml = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new ImitationSourceNotFoundException(IMITATION_SOURCE_NOT_FOUND, e);
        }
        return converter.fromXML(xml);
    }

    public static String toXML(ImitationSource source, XMLImitationSourceConverter converter) {
        String path = "data/rout_" + Instant.now() + ".xml";
        return converter.toXML(source, path);
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

