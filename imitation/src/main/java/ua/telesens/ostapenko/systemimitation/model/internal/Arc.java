package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;

/**
 * @author root
 * @since 09.12.15
 */
@Data
@AllArgsConstructor
public class Arc {

    private StationObserver initialStation;
    private StationObserver finalStation;
    private int interval;

}
