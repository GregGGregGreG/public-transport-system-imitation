package ua.telesens.ostapenko.systemimitation.model.internal;

import ua.telesens.ostapenko.systemimitation.api.StationRule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author root
 * @since 10.12.15
 */
public class BusStationRule implements StationRule {

    private int limitPassenger;
    private LocalTime starting;
    private LocalTime end;
    private LocalTime interval;
    private List<LocalTime> schedule = Collections.emptyList();

    public BusStationRule(int limitPassenger, LocalTime starting, LocalTime end, LocalTime interval) {
        this.limitPassenger = limitPassenger;
        this.starting = starting;
        this.end = end;
        this.interval = interval;
        fillSchedule();
    }

    @Override
    public boolean is(LocalTime time) {
        if (Objects.equals(time, starting) || Objects.equals(time, end)) {
            if (schedule.contains(time)) {
                return true;
            }
        }
        if (time.isAfter(starting) && (time.isBefore(end))) {
            if (schedule.contains(time)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int execute(LocalDateTime time) {
        if (!is(time.toLocalTime())) {
            throw new IllegalStateException();
        }
        return new Random().nextInt(limitPassenger);
    }

    private void fillSchedule() {
        schedule = new ArrayList<>();
        int starting = this.starting.toSecondOfDay();
        int end = this.end.toSecondOfDay();
        int interval = this.interval.toSecondOfDay();

        for (int i = starting; i <= end; i = i + interval) {
            schedule.add(LocalTime.ofSecondOfDay(i));
        }
    }
}
