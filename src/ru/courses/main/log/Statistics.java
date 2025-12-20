package ru.courses.main.log;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Statistics {
    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = LocalDateTime.MAX;
        this.maxTime = LocalDateTime.MIN;
    }

    public void addEntry(LogEntry log) {
        this.totalTraffic += log.getDataSize();
        if (log.getTime().isBefore(this.minTime)) this.minTime = log.getTime();
        if (log.getTime().isAfter(this.maxTime)) this.maxTime = log.getTime();
    }

    public double getTrafficRate() {
        long durationInMinutes = ChronoUnit.MINUTES.between(this.minTime, this.maxTime);
        double durationInHours = durationInMinutes / 60.0;
        if (durationInHours == 0.0) {
            return 0.0;
        }
        return (double) this.totalTraffic / durationInHours;
    }

    public int getTotalTraffic() {
        return totalTraffic;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return totalTraffic == that.totalTraffic
                && Objects.equals(minTime, that.minTime)
                && Objects.equals(maxTime, that.maxTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalTraffic, minTime, maxTime);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "totalTraffic=" + totalTraffic +
                ", minTime=" + minTime +
                ", maxTime=" + maxTime +
                '}';
    }
}