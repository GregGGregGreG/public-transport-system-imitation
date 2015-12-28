package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;

import java.time.LocalTime;
import java.util.Collection;

/**
 * @author root
 * @since 23.11.15
 */
@Getter
@EqualsAndHashCode(exclude = {"arcList","busList","ending"})
@Builder
public class Route {

    private String name;
    private Collection<Arc> arcList;
    private Collection<BusObserver> busList;
    private long interval;
    private RouteType type;
    private int countRace;
    private long timeOut;
    private LocalTime starting;
    @Setter
    private LocalTime ending;

    @Override
    public String toString() {
        return "Route{" +
                "name='" + name + '\'' +
                '}';
    }
}