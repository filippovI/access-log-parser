package ru.courses.main;

import ru.courses.main.exceptions.LogEntryException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {
    String ipAddress, referer, requestPath;
    LocalDateTime time;
    HttpMethod requestMethod;
    UserAgent userAgent;
    int statusCode;
    int dataSize;
    private static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
    private static final Pattern LOG_PATTERN = Pattern.compile(
            "(?<ipAddress>\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})"
                    + "[^\"]*"
                    + "\\[(?<datetime>\\d{2}/[A-Za-z]{3}/\\d{4}:\\d{2}:\\d{2}:\\d{2}\\s[+\\-]\\d{4})]"
                    + "\\s+"
                    + "\"(?<requestMethod>[A-Z]+)\\s(?<requestPath>[^\"\\s]+)\\sHTTP/\\d\\.\\d\""
                    + "\\s+"
                    + "(?<statusCode>\\d{3})"
                    + "\\s+"
                    + "(?<responseSize>\\d+|-)"
                    + "\\s+"
                    + "\"(?<referer>[^\"]*)\""
                    + "\\s"
                    + "\"(?<userAgent>([^\"]*))\"");

    public LogEntry(String line) {
        if (line != null && !line.isEmpty()) {
            try {
                Matcher matcher = LOG_PATTERN.matcher(line);
                if (matcher.find()) {
                    this.ipAddress = matcher.group("ipAddress");
                    this.time = LocalDateTime.parse(matcher.group("datetime"), TIME_PATTERN);
                    this.requestMethod = HttpMethod.fromText(matcher.group("requestMethod"));
                    this.requestPath = matcher.group("requestPath");
                    this.statusCode = Integer.parseInt(matcher.group("statusCode"));
                    this.dataSize = Integer.parseInt(matcher.group("responseSize"));
                    this.referer = matcher.group("referer");
                    this.userAgent = new UserAgent(matcher.group("userAgent"));
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
