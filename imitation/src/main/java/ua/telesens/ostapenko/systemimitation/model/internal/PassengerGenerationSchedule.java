package ua.telesens.ostapenko.systemimitation.model.internal;

import com.google.common.collect.Iterables;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author root
 * @since 09.01.16
 */
public class PassengerGenerationSchedule {

    private final int count;
    @Getter
    private final LocalTime start;
    @Getter
    private LocalTime end;
    @Getter
    private final LocalTime interval;
    @Getter
    private List<LocalTime> schedule = new ArrayList<>();

    private PassengerGenerationSchedule(PassengerGenerationRule rule) {
        this.start = rule.getStart();
        this.interval = rule.getInterval();
        this.end = rule.getEnd();
        this.count = rule.getCount();
        fillSchedule();
    }

    public static PassengerGenerationSchedule of(PassengerGenerationRule rule) {
        return new PassengerGenerationSchedule(rule);
    }

    public boolean is(LocalTime time) {
        if (Objects.equals(time, start) || Objects.equals(time, end)) {
            if (schedule.contains(time)) {
                return true;
            }
        }
        if (time.isAfter(start) && (time.isBefore(end))) {
            if (schedule.contains(time)) {
                return true;
            }
        }
        return false;
    }

    private void fillSchedule() {
        int starting = this.start.toSecondOfDay();
        int end = this.end.toSecondOfDay();
        int interval = this.interval.toSecondOfDay();

        for (int i = starting; i <= end; i = i + interval) {
            schedule.add(LocalTime.ofSecondOfDay(i));
        }

        this.end = Iterables.getLast(schedule);
    }

    public int getPassengerGenerationCount(LocalTime time) {
        if (!is(time)) {
            throw new IllegalArgumentException();
        }
        return count;
    }
}
