package ru.courses.main.patterns;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class PatternsForLogParsing {
    public static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
    public static final Pattern LOG_PATTERN = Pattern.compile(
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
    public static final Pattern BROWSER_SAFARI_PATTERN = Pattern.compile("(?i)Safari/(\\d+(\\.\\d+)*)");
    public static final Pattern OPERATION_SYSTEM_PATTERN = Pattern.compile("\\(([^)]+?)\\)");
    //Нужно сохранить порядок
    public static final Map<String, Pattern> BROWSER_PATTERN_MAP = new LinkedHashMap<>() {{
        put("Edge", Pattern.compile("(?i)Edge/(\\d+(\\.\\d+)*)"));
        put("Opera", Pattern.compile("(?i)(Opera|OPR)/(\\d+(\\.\\d+)*)"));
        put("Chrome", Pattern.compile("(?i)Chrome/(\\d+(\\.\\d+)*)"));
        put("Firefox", Pattern.compile("(?i)Firefox/(\\d+(\\.\\d+)*)"));
        put("Internet Explorer", Pattern.compile("(?i)(MSIE\\s(\\d+\\.\\d+))|(Trident).*?((rv:)(\\d+\\.\\d+))"));
    }};
    public static final Pattern IS_BOT_PATTERN = Pattern.compile(".*\\(([^()]*)\\)[^()]*$");
    public static final Pattern DOMAIN_PATTERN = Pattern.compile("(?:https?://|ftp://)?(?:www\\.)?([^/:?#&]+)");
}
