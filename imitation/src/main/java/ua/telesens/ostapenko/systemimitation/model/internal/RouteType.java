package ua.telesens.ostapenko.systemimitation.model.internal;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * @author root
 * @since 09.12.15
 */
public enum RouteType {

    LINE {
        @Override
        public Map<RouteDirection, Queue<Passenger>> getQue() {
            Map<RouteDirection, Queue<Passenger>> que = new HashMap<>();
            que.put(RouteDirection.STRAIGHT, new ArrayDeque<>());
            return que;
        }
    }, CYCLE {
        @Override
        public Map<RouteDirection, Queue<Passenger>> getQue() {
            Map<RouteDirection, Queue<Passenger>> que = new HashMap<>();
            for (RouteDirection direction : RouteDirection.values()) {
                que.put(direction, new ArrayDeque<>());
            }
            return que;
        }
    };

    public abstract Map<RouteDirection, Queue<Passenger>> getQue();

}
