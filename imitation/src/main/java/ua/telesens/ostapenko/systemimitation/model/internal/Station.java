package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.*;

/**
 * @author root
 * @since 23.11.15
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
public class Station {

    @Getter
    @Setter
    @NonNull
    private String name;

}
