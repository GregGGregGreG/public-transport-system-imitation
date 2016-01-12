package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author root
 * @since 11.01.16
 */
@Data
@Builder
public class LogBus {

    private int busNUmber;
    private LocalDateTime timeStop;
    private UUID stationUUID;
    private String routeName;
    private int download;
    private int upload;
    private int count;

}
