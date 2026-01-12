package ru.courses.main.log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private final HashSet<String> existSites;
    private final HashSet<String> noExistSites;
    private final HashMap<String, Integer> operationSystemsFrequency;
    private final HashMap<String, Integer> browsersFrequency;
    private final Set<String> uniqueIpAddressesList;
    private int usersAreNotBotsCount;
    private int errorRequests;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
        this.existSites = new HashSet<>();
        this.operationSystemsFrequency = new HashMap<>();
        this.noExistSites = new HashSet<>();
        this.browsersFrequency = new HashMap<>();
        this.usersAreNotBotsCount = 0;
        this.errorRequests = 0;
        this.uniqueIpAddressesList = new HashSet<>();
    }

    public void addEntry(LogEntry log) {
        if (log != null) {
            this.totalTraffic += log.getDataSize();
            if (minTime == null && maxTime == null) {
                minTime = log.getTime();
                maxTime = log.getTime();
            } else {
                if (log.getTime().isBefore(minTime)) minTime = log.getTime();
                if (log.getTime().isAfter(maxTime)) maxTime = log.getTime();
            }

            if (log.getStatusCode() == 200) existSites.add(log.getPath());
            if (log.getStatusCode() / 100 == 4 || log.getStatusCode() / 100 == 5) {
                noExistSites.add(log.getPath());
                errorRequests++;
            }

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

            if (!log.getUserAgent().isBot()) {
                usersAreNotBotsCount++;
                if (log.getIpAddress() != null && !log.getIpAddress().isEmpty())
                    uniqueIpAddressesList.add(log.getIpAddress());
            }
        }
    }

    public BigDecimal getTrafficRate() {
        return getAveragePerHour(totalTraffic, 3);
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

    public int getUsersAreNotBotsCount() {
        return usersAreNotBotsCount;
    }

    public Set<String> getUniqueIpAddressesList() {
        return uniqueIpAddressesList;
    }

    public int getErrorRequests() {
        return errorRequests;
    }

    public HashMap<String, BigDecimal> getOperationSystemsFrequency() {
        return getFrequency(operationSystemsFrequency);
    }

    public HashMap<String, BigDecimal> getBrowsersFrequency() {
        return getFrequency(browsersFrequency);
    }

    public BigDecimal getAverageOfVisitsPerHour() {
        return getAveragePerHour(usersAreNotBotsCount);

    }

    public BigDecimal getAverageErrorRequestsPerHour() {
        return getAveragePerHour(errorRequests);

    }

    public BigDecimal getAverageTrafficPerUser() {
        String result = String.valueOf((double) usersAreNotBotsCount / uniqueIpAddressesList.size());
        result = result.equals("NaN") || result.equals("Infinity") ? "0" : result;
        return new BigDecimal(result)
                .setScale(3, RoundingMode.HALF_UP);
    }

    private BigDecimal getAveragePerHour(long data) {
        return getAveragePerHour(data, 3);
    }

    private BigDecimal getAveragePerHour(long data, int scale) {
        if (minTime == null || maxTime == null) return new BigDecimal("0.0");
        long durationInMinutes = ChronoUnit.MINUTES.between(minTime, maxTime);
        double durationInHours = durationInMinutes / 60.0;
        if (durationInHours == 0.0) {
            return new BigDecimal("0.0");
        }
        return new BigDecimal(String.valueOf((double) data / durationInHours))
                .setScale(scale, RoundingMode.HALF_UP);
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
                ", uniqueIpAddressesList=" + uniqueIpAddressesList +
                ", usersAreNotBotsCount=" + usersAreNotBotsCount +
                ", errorRequests=" + errorRequests +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return totalTraffic == that.totalTraffic
                && usersAreNotBotsCount == that.usersAreNotBotsCount
                && errorRequests == that.errorRequests
                && Objects.equals(minTime, that.minTime)
                && Objects.equals(maxTime, that.maxTime)
                && Objects.equals(existSites, that.existSites)
                && Objects.equals(noExistSites, that.noExistSites)
                && Objects.equals(operationSystemsFrequency, that.operationSystemsFrequency)
                && Objects.equals(browsersFrequency, that.browsersFrequency)
                && Objects.equals(uniqueIpAddressesList, that.uniqueIpAddressesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalTraffic, minTime, maxTime);
    }
}