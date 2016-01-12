package ua.telesens.ostapenko.systemimitation.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.model.internal.*;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.IntStream;

import static ua.telesens.ostapenko.systemimitation.service.BusSystemImitation.MAX_TIME_PASSENGER_WAITING;
import static ua.telesens.ostapenko.systemimitation.service.BusSystemImitation.MIN_TIME_PASSENGER_WAITING;

/**
 * @author root
 * @since 08.01.16
 */
@Slf4j
public class PassengerGenerator {

    private final StationObserver observer;
    @Getter
    private Map<DayType, List<PassengerGenerationSchedule>> schedule = new HashMap<>();
    private List<RouteDecorator> routes;
    private Random random = new Random();

    public PassengerGenerator(StationObserver observer) {
        this.observer = observer;
        this.routes = observer.getRoutes();
        createSchedule();
    }

    public List<Passenger> execute(DayType dayType, LocalTime time) {
        List<Passenger> result = new ArrayList<>();
        schedule.get(dayType)
                .stream()
                .parallel()
                .filter(schedule -> schedule.is(time))
                .forEach(schedule -> result.addAll(genPassengers(schedule.getPassengerGenerationCount(time))));
        return result;
    }

    private List<Passenger> genPassengers(int count) {
        List<Passenger> result = new ArrayList<>();
        int randCount = random.nextInt(count);
        IntStream.range(0, randCount).forEach(e -> result.add(genPassenger()));
        return result;
    }

    private Passenger genPassenger() {
        RouteDecorator route = getRandomRoute();
        RouteDirection direction = RouteDirection.getRandom(route, observer);
        StationObserver stationFinal = direction.toStation(route, observer)
                .stream()
                .findFirst()
                .get();

        int randTimeWait = random
                .ints(MIN_TIME_PASSENGER_WAITING, MAX_TIME_PASSENGER_WAITING)
                .findFirst()
                .getAsInt();

        LocalTime waiting = LocalTime.of(0, 0).plusMinutes(randTimeWait);

        Passenger passenger = Passenger.of(route, direction, stationFinal, waiting);

        log.trace(observer.getStation().getName() + "Gen new passenger\t" + direction + "\t" + passenger);
        return passenger;
    }

    private RouteDecorator getRandomRoute() {
        List<RouteDecorator> buff = new ArrayList<>();
        buff.addAll(routes);
        Collections.shuffle(buff);
        return buff.stream().findFirst().get();
    }

    private void createSchedule() {
        observer.getStation().getRules().forEach(this::handlerRule);
    }

    private void handlerRule(DayType dayType, PassengerGenerationRuleList rules) {
        List<PassengerGenerationSchedule> result = new ArrayList<>();
        rules.getPassengerGenerationRules()
                .stream()
                .forEach(rule -> result.add(PassengerGenerationSchedule.of(rule)));
        schedule.put(dayType, result);
    }
}
