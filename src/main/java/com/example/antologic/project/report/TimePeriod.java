package com.example.antologic.project.report;

public enum TimePeriod {
    WEEK("WEEK"),
    MONTH("MONTH"),
    YEAR("YEAR");

    private final String timePeriod;

    TimePeriod(final String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getTimePeriod() {
        return timePeriod;
    }
}
