package ua.telesens.ostapenko.systemimitation.service;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteList;
import ua.telesens.ostapenko.systemimitation.util.RouteGenerator;

import java.io.File;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * @author root
 * @since 07.01.16
 */
public class RouteParserTest {

    private static final LocalDateTime STARTING = LocalDateTime.of(2015, Month.NOVEMBER, 12, 6, 0);
    private static final LocalDateTime END = LocalDateTime.of(2015, Month.NOVEMBER, 15, 23, 0);
    private RouteList source;

    @Before
    public void setUp() throws Exception {
        source = RouteGenerator.get();
    }

    @Test
    public void fromXML() throws Exception {
        File file = new File("route.xml");
        RouteParser parser = new RouteParser();
        RouteList target = parser.fromXML(parser.toXML(source, file));
        BusSystemImitation imitation = new BusSystemImitation(target, STARTING, END);
//        BusSystemImitationStatistic busSystemImitationStatistic = BusSystemImitationStatistic.of(imitation);
//        busSystemImitationStatistic.execute();
        TestCase.assertEquals(source, target);
    }

    @Test
    public void toXML() throws Exception {
        File file = new File("route.xml");
        RouteParser parser = new RouteParser();
        parser.toXML(source, file);
    }
}