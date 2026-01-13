package ru.courses.main.log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.regex.Matcher;

import static ru.courses.main.patterns.PatternsForLogParsing.DOMAIN_PATTERN;


public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private final HashMap<String, Integer> operationSystemsFrequencyMap;
    private final HashMap<String, Integer> browsersFrequencyMap;
    private final HashMap<String, Integer> countVisitsPerSecondMap;
    private final HashMap<String, Integer> countMaximumVisitsByOneUserMap;
    private final HashSet<String> refererSet;
    private final HashSet<String> uniqueIpAddressesSet;
    private final HashSet<String> existSitesSet;
    private final HashSet<String> noExistSitesSet;
    private int usersAreNotBotsCount;
    private int errorRequests;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
        this.existSitesSet = new HashSet<>();
        this.operationSystemsFrequencyMap = new HashMap<>();
        this.noExistSitesSet = new HashSet<>();
        this.browsersFrequencyMap = new HashMap<>();
        this.usersAreNotBotsCount = 0;
        this.errorRequests = 0;
        this.uniqueIpAddressesSet = new HashSet<>();
        this.countVisitsPerSecondMap = new HashMap<>();
        this.refererSet = new HashSet<>();
        this.countMaximumVisitsByOneUserMap = new HashMap<>();
    }

    public void addEntry(LogEntry log) {
        if (log != null) {
            this.totalTraffic += log.getDataSize();
            setTime(log);
            addExistsAndNoExistsSites(log);
            addCountInMap(log.getUserAgent().getOperationSystem(), operationSystemsFrequencyMap);
            addCountInMap(log.getUserAgent().getBrowser(), browsersFrequencyMap);
            addCountInMap(log.getIpAddress(), countMaximumVisitsByOneUserMap);
            addDomainInRefererSet(log);

            if (!log.getUserAgent().isBot()) {
                //Наращиваем каунтер запросов - не ботов
                usersAreNotBotsCount++;

                //Добавляем ip адрес в uniqueIpAddressesSet
                if (log.getIpAddress() != null && !log.getIpAddress().isEmpty())
                    uniqueIpAddressesSet.add(log.getIpAddress());

                //Считаем количество запросов в секунду
                if (log.getTime() != null) {
                    addCountInMap(String.valueOf(log.getTime().getSecond()), countVisitsPerSecondMap);
                }
            }
        }
    }

    public BigDecimal getTrafficRate() {
        return getAverage(totalTraffic, 3);
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

    public HashSet<String> getExistSitesSet() {
        return new HashSet<>(existSitesSet);
    }

    public HashSet<String> getNoExistSitesSet() {
        return new HashSet<>(noExistSitesSet);
    }

    public int getUsersAreNotBotsCount() {
        return usersAreNotBotsCount;
    }

    public int getErrorRequests() {
        return errorRequests;
    }

    public HashMap<String, Integer> getCountVisitsPerSecondMap() {
        return new HashMap<>(countVisitsPerSecondMap);
    }

    public HashSet<String> getRefererSet() {
        return new HashSet<>(refererSet);
    }

    public HashMap<String, Integer> getCountMaximumVisitsByOneUserMap() {
        return new HashMap<>(countMaximumVisitsByOneUserMap);
    }

    public HashMap<String, Integer> getBrowsersFrequencyMap() {
        return new HashMap<>(browsersFrequencyMap);
    }

    public HashMap<String, Integer> getOperationSystemsFrequencyMap() {
        return new HashMap<>(operationSystemsFrequencyMap);
    }

    public HashSet<String> getUniqueIpAddressesSet() {
        return new HashSet<>(uniqueIpAddressesSet);
    }

    public int getPeakVisitPerSecond() {
        return getMaxFromMap(countVisitsPerSecondMap);
    }

    public int getCountMaximumVisitsByOneUser() {
        return getMaxFromMap(countMaximumVisitsByOneUserMap);
    }

    public HashMap<String, BigDecimal> getOperationSystemsFrequency() {
        return getFrequency(operationSystemsFrequencyMap);
    }

    public HashMap<String, BigDecimal> getBrowsersFrequency() {
        return getFrequency(browsersFrequencyMap);
    }

    public BigDecimal getAverageOfVisitsPerHour() {
        return getAverage(usersAreNotBotsCount);
    }

    public BigDecimal getAverageErrorRequestsPerHour() {
        return getAverage(errorRequests);
    }

    public BigDecimal getAverageTrafficPerUser() {
        String result = String.valueOf((double) usersAreNotBotsCount / uniqueIpAddressesSet.size());
        result = result.equals("NaN") || result.equals("Infinity") ? "0" : result;
        return new BigDecimal(result)
                .setScale(3, RoundingMode.HALF_UP);
    }

    private BigDecimal getAverage(long data) {
        return getAverage(data, 3);
    }

    private BigDecimal getAverage(long data, int scale) {
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
        return getFrequency(valuesMap, 8);
    }

    private HashMap<String, BigDecimal> getFrequency(HashMap<String, Integer> valuesMap, int scale) {
        HashMap<String, BigDecimal> result = new HashMap<>();
        //Округление до scale знаков
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

    private <T> int getMaxFromMap(HashMap<T, Integer> map) {
        return map
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
    }

    private void setTime(LogEntry log) {
        if (minTime == null && maxTime == null) {
            minTime = log.getTime();
            maxTime = log.getTime();
        } else {
            if (log.getTime().isBefore(minTime)) minTime = log.getTime();
            if (log.getTime().isAfter(maxTime)) maxTime = log.getTime();
        }
    }

    private void addExistsAndNoExistsSites(LogEntry log) {
        if (log.getStatusCode() == 200) existSitesSet.add(log.getPath());
        if (log.getStatusCode() / 100 == 4 || log.getStatusCode() / 100 == 5) {
            noExistSitesSet.add(log.getPath());
            errorRequests++;
        }
    }

    private void addCountInMap(String keyName, HashMap<String, Integer> map) {
        if (keyName != null && !keyName.isEmpty()) {
            if (!map.containsKey(keyName))
                map.put(keyName, 1);
            else
                map.put(keyName, map.get(keyName) + 1);
        }
    }

    private void addDomainInRefererSet(LogEntry log) {
        if (log.getReferer() != null && !log.getReferer().isEmpty() && !log.getReferer().equals("-")) {
            String format_url = URLDecoder.decode(log.getReferer(), StandardCharsets.UTF_8);
            Matcher matcher = DOMAIN_PATTERN.matcher(format_url);
            if (matcher.find()) {
                refererSet.add(URLDecoder.decode(matcher.group(1), StandardCharsets.UTF_8));
            }
        }
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "totalTraffic=" + totalTraffic +
                ", minTime=" + minTime +
                ", maxTime=" + maxTime +
                ", operationSystemsFrequencyMap=" + operationSystemsFrequencyMap +
                ", browsersFrequencyMap=" + browsersFrequencyMap +
                ", countVisitsPerSecondMap=" + countVisitsPerSecondMap +
                ", countMaximumVisitsByOneUserMap=" + countMaximumVisitsByOneUserMap +
                ", refererSet=" + refererSet +
                ", uniqueIpAddressesSet=" + uniqueIpAddressesSet +
                ", existSitesSet=" + existSitesSet +
                ", noExistSitesSet=" + noExistSitesSet +
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
                && Objects.equals(operationSystemsFrequencyMap, that.operationSystemsFrequencyMap)
                && Objects.equals(browsersFrequencyMap, that.browsersFrequencyMap)
                && Objects.equals(countVisitsPerSecondMap, that.countVisitsPerSecondMap)
                && Objects.equals(countMaximumVisitsByOneUserMap, that.countMaximumVisitsByOneUserMap)
                && Objects.equals(refererSet, that.refererSet)
                && Objects.equals(uniqueIpAddressesSet, that.uniqueIpAddressesSet)
                && Objects.equals(existSitesSet, that.existSitesSet)
                && Objects.equals(noExistSitesSet, that.noExistSitesSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalTraffic, minTime, maxTime);
    }
}