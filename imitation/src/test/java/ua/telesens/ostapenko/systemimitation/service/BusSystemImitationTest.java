package ua.telesens.ostapenko.systemimitation.service;

import org.junit.Before;
import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteList;
import ua.telesens.ostapenko.systemimitation.util.RouteGenerator;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * @author root
 * @since 29.12.15
 */
public class BusSystemImitationTest {

    private static final LocalDateTime STARTING = LocalDateTime.of(2015, Month.NOVEMBER, 12, 6, 0);
    private static final LocalDateTime END = LocalDateTime.of(2015, Month.NOVEMBER, 15, 23, 0);
    private RouteList source;

    @Before
    public void setUp() throws Exception {
        source = RouteGenerator.get();
    }

    @Test
    public void run() throws Exception {
        BusSystemImitation imitation = new BusSystemImitation(source, STARTING, END);
    }

}