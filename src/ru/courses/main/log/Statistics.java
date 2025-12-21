package ru.courses.main.log;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Statistics {
    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private final HashSet<String> existSites;
    private final HashSet<String> noExistSites;
    private final HashMap<String, Integer> operationSystemsFrequency;
    private final HashMap<String, Integer> browsersFrequency;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
        this.existSites = new HashSet<>();
        this.operationSystemsFrequency = new HashMap<>();
        this.noExistSites = new HashSet<>();
        this.browsersFrequency = new HashMap<>();
    }

    public void addEntry(LogEntry log) {
        this.totalTraffic += log.getDataSize();
        if (minTime == null && maxTime == null) {
            minTime = log.getTime();
            maxTime = log.getTime();
        } else {
            if (log.getTime().isBefore(minTime)) minTime = log.getTime();
            if (log.getTime().isAfter(maxTime)) maxTime = log.getTime();
        }

        if (log.getStatusCode() == 200) existSites.add(log.getPath());
        if (log.getStatusCode() == 404) existSites.add(log.getPath());

        String operationSystemName = log.getUserAgent().getOperationSystem();
        if (operationSystemName != null && !operationSystemName.isEmpty()) {
            if (!operationSystemsFrequency.containsKey(operationSystemName))
                operationSystemsFrequency.put(operationSystemName, 1);
            else
                operationSystemsFrequency.put(operationSystemName, operationSystemsFrequency.get(operationSystemName) + 1);
        }

        String browserName = log.getUserAgent().getBrowser();
        if (browserName != null && !browserName.isEmpty()) {
            if (!browsersFrequency.containsKey(browserName))
                browsersFrequency.put(browserName, 1);
            else
                browsersFrequency.put(browserName, browsersFrequency.get(browserName) + 1);
        }
    }

    public double getTrafficRate() {
        if (minTime == null || maxTime == null) return 0.0;
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

    public HashSet<String> getExistSites() {
        return existSites;
    }

    public HashSet<String> getNoExistSites() {
        return noExistSites;
    }

    public HashMap<String, Integer> getOperationSystemsFrequency() {
        return operationSystemsFrequency;
    }

    public HashMap<String, Integer> getBrowsersFrequency() {
        return browsersFrequency;
    }

    private HashMap<String, Double> getFrequency(HashMap<String, Integer> map) {
        HashMap<String, Double> result = new HashMap<>();
        int sumValues = map
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        map.forEach((k, v) -> result.put(k, (double) v / sumValues));
        return result;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "totalTraffic=" + totalTraffic +
                ", minTime=" + minTime +
                ", maxTime=" + maxTime +
                ", existSites=" + existSites +
                ", noExistSites=" + noExistSites +
                ", operationSystemsFrequency=" + operationSystemsFrequency +
                ", browsersFrequency=" + browsersFrequency +
                '}';
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

}