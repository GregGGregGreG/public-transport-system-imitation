package ua.telesens.ostapenko.systemimitation.model.internal;

import junit.framework.TestCase;
import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.util.RouteListGenerator;

/**
 * @author root
 * @since 16.01.16
 */
public class RouteTest {

    @Test
    public void equalsTest() throws Exception {
        Route route = RouteListGenerator.get().getRoutes().get(0);
        TestCase.assertEquals(route, route);
    }

    @Test
    public void hashCodeTest() throws Exception {

    }


}