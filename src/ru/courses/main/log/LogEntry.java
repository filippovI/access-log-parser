package ru.courses.main.log;

import ru.courses.main.enums.HttpMethod;
import ru.courses.main.exceptions.LogEntryException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.regex.Matcher;

import static ru.courses.main.patterns.PatternsForLogParsing.LOG_PATTERN;
import static ru.courses.main.patterns.PatternsForLogParsing.TIME_PATTERN;


public class LogEntry {
    private final String ipAddress, referer, requestPath;
    private final LocalDateTime time;
    private final HttpMethod requestMethod;
    private final UserAgent userAgent;
    private final int statusCode, dataSize;

    private LogEntry(String ipAddress, String referer, String requestPath, LocalDateTime time,
                     HttpMethod requestMethod, UserAgent userAgent, int statusCode, int dataSize) {
        this.ipAddress = ipAddress;
        this.referer = referer;
        this.requestPath = requestPath;
        this.time = time;
        this.requestMethod = requestMethod;
        this.userAgent = userAgent;
        this.statusCode = statusCode;
        this.dataSize = dataSize;
    }

    public static LogEntry fromString(String line) {
        if (line != null && !line.isEmpty()) {
            try {
                Matcher matcher = LOG_PATTERN.matcher(line);
                if (matcher.find()) {
                    return new LogEntry(
                            matcher.group("ipAddress"),
                            matcher.group("referer"),
                            matcher.group("requestPath"),
                            LocalDateTime.parse(matcher.group("datetime"), TIME_PATTERN),
                            HttpMethod.fromText(matcher.group("requestMethod")),
                            UserAgent.fromString(matcher.group("userAgent")),
                            Integer.parseInt(matcher.group("statusCode")),
                            Integer.parseInt(matcher.group("responseSize")));
                }
            } catch (NumberFormatException ex) {
                throw new LogEntryException("Не удалось преобразовать строку в Integer", ex);
            } catch (IllegalArgumentException ex) {
                throw new LogEntryException("Не удалось найти group() в matcher", ex);
            } catch (DateTimeParseException ex) {
                throw new LogEntryException("Неверный формат даты", ex);
            } catch (Exception ex) {
                throw new LogEntryException("Ошибка инициализации параметров", ex);
            }
        }
        return null;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getReferer() {
        return referer;
    }

    public String getPath() {
        return requestPath;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return requestMethod;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getDataSize() {
        return dataSize;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "ipAddress='" + ipAddress + '\'' +
                ", referer='" + referer + '\'' +
                ", requestPath='" + requestPath + '\'' +
                ", time=" + time +
                ", requestMethod=" + requestMethod.getMethodName() +
                ", userAgent=" + userAgent +
                ", statusCode=" + statusCode +
                ", dataSize=" + dataSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEntry logEntry = (LogEntry) o;
        return statusCode == logEntry.statusCode
                && dataSize == logEntry.dataSize
                && Objects.equals(ipAddress, logEntry.ipAddress)
                && Objects.equals(referer, logEntry.referer)
                && Objects.equals(requestPath, logEntry.requestPath)
                && Objects.equals(time, logEntry.time)
                && requestMethod.getMethodName().equals(logEntry.requestMethod.getMethodName())
                && Objects.equals(userAgent, logEntry.userAgent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddress, referer, requestPath, time, requestMethod, userAgent, statusCode, dataSize);
    }
}
