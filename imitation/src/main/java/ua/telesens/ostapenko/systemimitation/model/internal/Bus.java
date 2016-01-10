package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;

/**
 * @author root
 * @since 23.11.15
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
@ToString(exclude = {"uuid", "capacity"})
@XStreamAlias("bus")
public class Bus {

    private UUID uuid = UUID.randomUUID();
    private final int number;
    private final int capacity;

}
