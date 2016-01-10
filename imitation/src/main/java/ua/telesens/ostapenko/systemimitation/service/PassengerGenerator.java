package ua.telesens.ostapenko.systemimitation.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.model.internal.*;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.IntStream;

/**
 * @author root
 * @since 08.01.16
 */
@Slf4j
public class PassengerGenerator {

    private final StationObserver observer;
    @Getter
    private Map<DayType, List<PassengerGenerationSchedule>> schedule = Collections.emptyMap();
    private List<RouteDecorator> routes = Collections.emptyList();
    private Random random;

    public PassengerGenerator(StationObserver observer) {
        this.observer = observer;
        this.routes = observer.getRoutes();
        this.schedule = new HashMap<>();
        this.random = new Random();
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
        IntStream.range(0, random.nextInt(count)).forEach(e -> result.add(genPassenger()));
        return result;
    }

    private Passenger genPassenger() {
        RouteDecorator route = getRandomRoute();
        RouteDirection direction = RouteDirection.getRandom(route, observer);
        StationObserver stationFinal = direction.toStation(route, observer)
                .stream()
                .findFirst()
                .get();

        Passenger passenger = Passenger.of(route, direction, stationFinal);

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
                .parallel()
                .forEach(rule -> result.add(PassengerGenerationSchedule.of(rule)));
        schedule.put(dayType, result);
    }
}
