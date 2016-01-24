package ua.telesens.ostapenko.systemimitation.service;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.model.internal.ImitationSource;
import ua.telesens.ostapenko.systemimitation.util.ImitationSourceGenerator;

/**
 * @author root
 * @since 07.01.16
 */
public class ImitationSourceParserTest {


    private ImitationSource source;
    private XStreamXMLImitationSourceConverter converter;

    @Before
    public void setUp() throws Exception {
        source = ImitationSourceGenerator.get();
        converter = new XStreamXMLImitationSourceConverter();
    }

    @Test
    public void fromXML() throws Exception {
        ImitationSource target = ImitationSourceParser.fromXML(ImitationSourceParser.toXML(source, converter), converter);
        BusSystemImitation imitation = new BusSystemImitation(target);
        BusSystemImitationStatistic busSystemImitationStatistic = BusSystemImitationStatistic.of(imitation);
        busSystemImitationStatistic.execute();
        TestCase.assertEquals(source, target);
    }

    @Test
    public void toXML() throws Exception {
        ImitationSourceParser.toXML(source, converter);
    }
//
//    @Test
//    public void toJSON() throws Exception {
//        ImitationSourceParser.toJSON(source,converter);
//    }
}