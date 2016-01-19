package ua.telesens.ostapenko.systemimitation.service;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author root
 * @since 19.01.16
 */
@Slf4j
public class ProgressBar {
    private static final String SEPARATOR = "\n-------------------------------";
    private String format = "%-30s%-20s";
    private LocalDateTime starting;
    private LocalDateTime end;
    private int imitationStep;

    public ProgressBar(LocalDateTime starting, LocalDateTime end, int imitationStep) {
        this.starting = starting;
        this.end = end;
        this.imitationStep = imitationStep;
    }

    public void start() {
        if (log.isInfoEnabled()) {
            System.out.println("---- Imitation information ----");
            System.out.println(String.format(format, "Start imitation", starting.toString()));
            System.out.println(String.format(format, "Stop imitation", end.toString()));
        }
    }

    public void step(LocalDateTime imitationTime) {
        if (log.isInfoEnabled()) {
            System.out.print(String.format(format, "\rImitation time", imitationTime.toString()));
        }
        if (log.isInfoEnabled() &&
                imitationTime.plusMinutes(imitationStep).isEqual(end) ||
                imitationTime.plusMinutes(imitationStep).isAfter(end)) {
            System.out.println(SEPARATOR);
        }
    }
}
