package ua.telesens.ostapenko.systemimitation.service;

import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.api.StationRule;
import ua.telesens.ostapenko.systemimitation.model.internal.*;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * @author root
 * @since 10.12.15
 */
public class TransportImitationTest {

    @Test
    public void testRunImitation() throws Exception {
        List<Arc> arcRoute = new ArrayList<>();
        List<BusObserver> buses = new ArrayList<>();
        List<Route> routes = new ArrayList<>();

        LocalDateTime starting = LocalDateTime.of(2014, Month.JANUARY, 1, 6, 0, 0);
        LocalDateTime end = LocalDateTime.of(2014, Month.JANUARY, 2,5, 1, 0);

        BusSystemImitation imitation = new BusSystemImitation(routes, starting, end);

        LocalTime startingGen = LocalTime.of(6, 0);
        LocalTime endGen = LocalTime.of(21, 30);
        LocalTime interval = LocalTime.of(0, 7);

        BusStationRule busStopRule = new BusStationRule(8, startingGen, endGen, interval);
        List<StationRule> busStationRules = new ArrayList<>();
        busStationRules.add(busStopRule);

//        Create stop
        StationObserver stopA = StationObserver.of(Station.of("A"), busStationRules);

        stopA.setObservable(imitation);

        StationObserver stopB = StationObserver.of(Station.of("B"), busStationRules);

        stopB.setObservable(imitation);

        StationObserver stopC = StationObserver.of(Station.of("C"), busStationRules);

        stopC.setObservable(imitation);

        StationObserver stopD = StationObserver.of(Station.of("D"), busStationRules);

        stopD.setObservable(imitation);

        //Create ARCS
        arcRoute.add(new Arc(stopA, stopB, 5));
        arcRoute.add(new Arc(stopB, stopC, 10));
        arcRoute.add(new Arc(stopC, stopD, 23));
        //Create Bus
        BusObserver busOne = BusObserver.of(Bus.of("1", 450));
        busOne.setObservable(imitation);

        BusObserver busTwo = BusObserver.of(Bus.of("2", 450));
//        busTwo.setObservable(imitation);

        buses.add(busOne);
//        buses.add(busTwo);
        //Create route
        Route route_232 = Route.builder()
                .name("232")
                .arcList(arcRoute)
                .busList(buses)
                .starting(LocalTime.of(6, 0))
                .interval(20)
                .timeOut(20)
                .countRace(20)
                .type(RouteType.LINE)
                .build();
        routes.add(route_232);
        assertEquals("dsfds", imitation.run());
    }
}