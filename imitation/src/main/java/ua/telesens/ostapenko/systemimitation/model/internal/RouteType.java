package ua.telesens.ostapenko.systemimitation.model.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author root
 * @since 09.12.15
 */
public enum RouteType {

    LINE {
        @Override
        public Map<RouteDirection, ConcurrentLinkedDeque<Passenger>> getQue() {
            Map<RouteDirection, ConcurrentLinkedDeque<Passenger>> que = new HashMap<>();
            que.put(RouteDirection.STRAIGHT, new ConcurrentLinkedDeque<>());
            return que;
        }
    }, CYCLE {
        @Override
        public Map<RouteDirection, ConcurrentLinkedDeque<Passenger>> getQue() {
            Map<RouteDirection, ConcurrentLinkedDeque<Passenger>> que = new HashMap<>();
            for (RouteDirection direction : RouteDirection.values()) {
                que.put(direction, new ConcurrentLinkedDeque<>());
            }
            return que;
        }
    };

    public abstract Map<RouteDirection, ConcurrentLinkedDeque<Passenger>> getQue();

}
