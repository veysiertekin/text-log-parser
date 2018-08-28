package com.ef.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class ParserArgsBuilder {
    private Optional<String> startDate = empty();
    private Optional<String> duration = empty();
    private Optional<String> threshold = empty();

    public ParserArgsBuilder setStartDate(String startDate) {
        this.startDate = ofNullable(startDate);
        return this;
    }

    public ParserArgsBuilder setDuration(String duration) {
        this.duration = ofNullable(duration);
        return this;
    }

    public ParserArgsBuilder setThreshold(String threshold) {
        this.threshold = ofNullable(threshold);
        return this;
    }

    public String[] build() {
        final List<String> args = new ArrayList<>();
        startDate.ifPresent(startDate -> args.add("-startDate=" + startDate));
        duration.ifPresent(duration -> args.add("-duration=" + duration));
        threshold.ifPresent(threshold -> args.add("-threshold=" + threshold));

        return args.toArray(new String[0]);
    }
}
