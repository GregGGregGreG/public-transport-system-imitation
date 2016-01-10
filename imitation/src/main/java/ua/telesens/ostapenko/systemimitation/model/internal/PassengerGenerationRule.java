package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

/**
 * @author root
 * @since 10.12.15
 */
@Data
@Getter
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
@XStreamAlias("passengerGenerationRule")
public class PassengerGenerationRule {

    private UUID uuid = UUID.randomUUID();
    private final int count;
    private final LocalTime start;
    private final LocalTime end;
    private final LocalTime interval;

}
