package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ua.telesens.ostapenko.systemimitation.validation.ImitationSourceStep1;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author root
 * @since 23.11.15
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode(exclude = {"uuid","capacity"})
@ToString(exclude = {"uuid", "capacity"})
@XStreamAlias("bus")
public class Bus {

    @NotNull(groups = ImitationSourceStep1.class)
    private UUID uuid = UUID.randomUUID();

    @Min(value = 0, groups = ImitationSourceStep1.class)
    @Max(value = 10000, groups = ImitationSourceStep1.class)
    private final int number;

    @Min(value = 0, groups = ImitationSourceStep1.class)
    @Max(value = 70, groups = ImitationSourceStep1.class)
    private final int capacity;

}
