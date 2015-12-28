package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.*;

/**
 * @author root
 * @since 23.11.15
 */
@Data
@RequiredArgsConstructor(staticName = "of")
public class Bus {

    @Setter
    @Getter
    @NonNull
    private String number;

    @Getter
    @NonNull
    private int countPassenger;

}
