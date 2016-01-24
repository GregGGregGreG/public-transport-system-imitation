package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import ua.telesens.ostapenko.systemimitation.validation.ImitationSourceStep1;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
@ToString(exclude = "uuid")
@XStreamAlias("passengerGenerationRule")
public class PassengerGenerationRule {

    @NotNull(groups = ImitationSourceStep1.class)
    private UUID uuid = UUID.randomUUID();

    @Min(value = 1, groups = ImitationSourceStep1.class)
    @Max(value = 1000, groups = ImitationSourceStep1.class)
    private final int count;

    @NotNull(groups = ImitationSourceStep1.class)
    private final LocalTime start;

    @NotNull(groups = ImitationSourceStep1.class)
    private final LocalTime end;

    @NotNull(groups = ImitationSourceStep1.class)
    private final LocalTime interval;

}
