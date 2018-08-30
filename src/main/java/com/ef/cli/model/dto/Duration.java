package com.ef.cli.model.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public enum Duration {
    daily(ChronoUnit.DAYS), hourly(ChronoUnit.HOURS);

    private static final long ONE = 1L;
    private final ChronoUnit chronoUnit;

    Duration(ChronoUnit chronoUnit) {
        this.chronoUnit = chronoUnit;
    }

    public LocalDateTime addTo(LocalDateTime datetime) {
        return datetime.plus(ONE, chronoUnit);
    }
}
