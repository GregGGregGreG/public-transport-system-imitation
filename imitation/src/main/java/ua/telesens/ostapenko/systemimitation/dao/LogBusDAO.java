package ua.telesens.ostapenko.systemimitation.dao;

import org.springframework.stereotype.Component;
import ua.telesens.ostapenko.systemimitation.model.internal.LogBus;

/**
 * @author root
 * @since 11.01.16
 */
@Component
public interface LogBusDAO {

    int insertLogBus(LogBus add);

}
