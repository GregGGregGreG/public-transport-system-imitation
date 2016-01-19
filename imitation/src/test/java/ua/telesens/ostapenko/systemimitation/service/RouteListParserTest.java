package ua.telesens.ostapenko.systemimitation.service;

import org.junit.Before;
import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteList;
import ua.telesens.ostapenko.systemimitation.util.RouteListGenerator;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * @author root
 * @since 07.01.16
 */
public class RouteListParserTest {

    private static final LocalDateTime STARTING = LocalDateTime.of(2015, Month.NOVEMBER, 12, 6, 0);
    private static final LocalDateTime END = LocalDateTime.of(2015, Month.NOVEMBER, 15, 23, 0);
    private RouteList source;

    @Before
    public void setUp() throws Exception {
        source = RouteListGenerator.get();
    }

    @Test
    public void fromXML() throws Exception {
//        RouteList target = RouteParser.fromXML(RouteParser.toXML(source));
//        RouteList target = RouteParser.fromXML(new File("data/rout_2016-01-15T11:08:31.471Z.xml"));
//        BusSystemImitation imitation = new BusSystemImitation(target, STARTING, END);
//        BusSystemImitationStatistic busSystemImitationStatistic = BusSystemImitationStatistic.of(imitation);
//        busSystemImitationStatistic.execute();
//        TestCase.assertEquals(source, target);
    }

    @Test
    public void toXML() throws Exception {
        RouteListParser.toXML(source);
    }

    @Test
    public void toJSON() throws Exception {
        RouteListParser.toJSON(source);
    }
}