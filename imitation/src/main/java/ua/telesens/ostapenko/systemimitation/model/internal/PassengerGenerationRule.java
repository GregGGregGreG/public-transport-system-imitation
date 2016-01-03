package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author root
 * @since 10.12.15
 */
@ToString(exclude = "schedule")
public class PassengerGenerationRule {

    private int limit;
    private LocalTime starting;
    private LocalTime end;
    private LocalTime interval;
    private List<LocalTime> schedule = Collections.emptyList();

    private PassengerGenerationRule(int limit, LocalTime starting, LocalTime end, LocalTime interval) {
        this.limit = limit;
        this.starting = starting;
        this.end = end;
        this.interval = interval;
        fillSchedule();
    }

    public static PassengerGenerationRule of(int limit, LocalTime starting, LocalTime end, LocalTime interval) {
        return new PassengerGenerationRule(limit, starting, end, interval);
    }

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

    public int execute(LocalDateTime time) {
        if (!is(time.toLocalTime())) {
            throw new IllegalStateException();
        }
        return new Random().nextInt(limit);
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
