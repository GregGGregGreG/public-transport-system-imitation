package ua.telesens.ostapenko.systemimitation.service;

import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.ProgressBar;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

/**
 * @author root
 * @since 19.01.16
 */
@Slf4j
public class ProgressBarImpl implements ProgressBar {
    private static final String SEPARATOR = "\n-------------------------------";
    private final StringBuilder lineProgress = new StringBuilder();
    private final char symbol = '#';
    private final char emptySymbol = '.';
    private final int length = 34;
    // equal 1 because first symbol is - separator - '[' ......]
    private int last = 1;
    private String format = "%-40s: %-20s";
    private LocalDateTime starting;
    private LocalDateTime end;
    private long endInMillis;
    private long startingInMillis;
    private long totalMillis;

    private LocalTime timer;

    /**
     * initialize progress bar properties.
     */
    public ProgressBarImpl(LocalDateTime starting, LocalDateTime end) {
        this.starting = starting;
        this.end = end;
        this.startingInMillis = getMillis(starting);
        this.endInMillis = getMillis(end);
        this.totalMillis = endInMillis - startingInMillis;
    }

    @Override
    public void start() {
        if (!log.isDebugEnabled()) {
            last = 1;
            timer = LocalTime.now();
            showInfo();
        }
    }

    /**
     * called whenever the progress bar needs to be updated.
     * that is whenever progress was made.
     *
     * @param imitationTime step value
     */
    @Override
    public void step(LocalDateTime imitationTime) {
        if (!log.isDebugEnabled()) {
            System.out.print(String.format(format, "\r" +
                    getProgressLine(imitationTime) +
                    getTimeInPercent(imitationTime) + "%",
                    getTime(imitationTime))
            );
            if (imitationTime.isEqual(end)) {
                System.out.print(String.format("%-10s%-3s", "", "[OK]"));
                System.out.println(SEPARATOR);
            }
        }
    }

    private StringBuilder getProgressLine(LocalDateTime imitationTime) {
        return lineProgress.length() == 0 ? initProgressLine() : updateProgressLine(imitationTime);
    }

    private StringBuilder updateProgressLine(LocalDateTime imitationTime) {
        int t = getTimeInPercent(imitationTime);
        int n = (t * length) / 100;

        if (last == n) {
            return lineProgress;
        }
        for (int i = last; i <= n; i++) {
            if (last == 0) continue;
            lineProgress.setCharAt(i, symbol);
            last = n;
        }
        return lineProgress;
    }

    private StringBuilder initProgressLine() {
        lineProgress.append("[");
        for (int i = 0; i < length; i++) {
            lineProgress.append(emptySymbol);
        }
        lineProgress.append("]");
        return lineProgress;
    }

    private int getTimeInPercent(LocalDateTime imitationTime) {
        long n = getMillis(imitationTime) - startingInMillis;
        return getPercent(n, totalMillis);
    }

    private int getPercent(long n, long total) {
        return (int) ((n * 100) / total);
    }

    private String getTime(LocalDateTime imitationTime) {
        Duration duration = Duration.between(timer, LocalTime.now());
        return String.format("%-15s/%-15s", imitationTime.toString(), duration.toString());
    }

    private long getMillis(LocalDateTime date) {
        return date.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
    }

    private void showInfo() {
        System.out.println("---- Imitation information ----");
        System.out.println(String.format(format, "Start imitation", starting.toString()));
        System.out.println(String.format(format, "Stop imitation", end.toString()));
        System.out.println("---- Progress information ----");
    }
}


