package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import ua.telesens.ostapenko.systemimitation.validation.ImitationSourceStep1;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author root
 * @since 23.11.15
 */
@EqualsAndHashCode(exclude = {"uuid"})
@RequiredArgsConstructor(staticName = "of")
@ToString(exclude = {"uuid", "rules"})
public class Station {

    public static final int MIN_LENGTH_NAME = 1;
    public static final int MAX_LENGTH_NAME = 1000;
    public static final int MIN_COUNT_RULES = 2;
    public static final int MAX_COUNT_RULES = 2;

    @Getter
    @NotNull(groups = ImitationSourceStep1.class)
    private UUID uuid = UUID.randomUUID();

    @Getter
    @NotNull(groups = ImitationSourceStep1.class)
    @Length(min = MIN_LENGTH_NAME, max = MAX_LENGTH_NAME, groups = ImitationSourceStep1.class)
    private final String name;

    @XStreamAlias("stationPassengerGenerationRules")
    @NotNull(groups = ImitationSourceStep1.class)
    @Size(min = MIN_COUNT_RULES, max = MAX_COUNT_RULES, groups = ImitationSourceStep1.class)
    @Valid
    private final List<PassengerGenerationRuleList> rules;

    public List<PassengerGenerationRuleList> getRules() {
        return Objects.isNull(rules) ? null : Collections.unmodifiableList(rules);
    }
}
