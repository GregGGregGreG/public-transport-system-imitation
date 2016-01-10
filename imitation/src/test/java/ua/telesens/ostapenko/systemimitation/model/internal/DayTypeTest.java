package ua.telesens.ostapenko.systemimitation.model.internal;

import junit.framework.TestCase;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * @author root
 * @since 04.01.16
 */
public class DayTypeTest {

    @Test
    public void to() throws Exception {
        LocalDateTime time = LocalDateTime.of(2015, Month.NOVEMBER, 12, 6, 0);
        TestCase.assertEquals(DayType.WORKDAY, DayType.to(time.getDayOfWeek()));
    }
}