package ru.courses.main.log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Statistics {
    private long totalTraffic;
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
        if (log!= null) {
            this.totalTraffic += log.getDataSize();
            if (minTime == null && maxTime == null) {
                minTime = log.getTime();
                maxTime = log.getTime();
            } else {
                if (log.getTime().isBefore(minTime)) minTime = log.getTime();
                if (log.getTime().isAfter(maxTime)) maxTime = log.getTime();
            }

            if (log.getStatusCode() == 200) existSites.add(log.getPath());
            if (log.getStatusCode() == 404) noExistSites.add(log.getPath());

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
    }

    public BigDecimal getTrafficRate() {
        if (minTime == null || maxTime == null) return new BigDecimal("0.0");
        long durationInMinutes = ChronoUnit.MINUTES.between(this.minTime, this.maxTime);
        // Округляем до scale знаков после запятой
        int scale = 3;
        double durationInHours = durationInMinutes / 60.0;
        if (durationInHours == 0.0) {
            return new BigDecimal("0.0");
        }
        return new BigDecimal(String.valueOf((double) this.totalTraffic / durationInHours))
                .setScale(scale, RoundingMode.HALF_UP);
    }

    public long getTotalTraffic() {
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

    public HashMap<String, BigDecimal> getOperationSystemsFrequency() {
        return getFrequency(operationSystemsFrequency);
    }

    public HashMap<String, BigDecimal> getBrowsersFrequency() {
        return getFrequency(browsersFrequency);
    }

    private HashMap<String, BigDecimal> getFrequency(HashMap<String, Integer> valuesMap) {
        HashMap<String, BigDecimal> result = new HashMap<>();

        //Округление до scale знаков
        int scale = 8;
        int sumValues = valuesMap
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
        valuesMap.forEach((k, v) -> result.put(k, new BigDecimal(String.valueOf((double) v / sumValues))
                .setScale(scale, RoundingMode.HALF_UP)));
        if (result.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add).intValue() != 1
                && sumValues != 0)
            throw new IllegalArgumentException("Сумма долей не равна 1");
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