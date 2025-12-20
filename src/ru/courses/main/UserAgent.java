package ru.courses.main;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgent {
    private static final Pattern BROWSER_SAFARI_PATTERN = Pattern.compile("(?i)Safari/(\\d+(\\.\\d+)*)");

    //Нужно сохранить порядок
    private static final Map<String, Pattern> BROWSER_PATTERN_MAP = new LinkedHashMap<>() {{
        put("Edge", Pattern.compile("(?i)Edge/(\\d+(\\.\\d+)*)"));
        put("Opera", Pattern.compile("(?i)(Opera|OPR)/(\\d+(\\.\\d+)*)"));
        put("Chrome", Pattern.compile("(?i)Chrome/(\\d+(\\.\\d+)*)"));
        put("Firefox", Pattern.compile("(?i)Firefox/(\\d+(\\.\\d+)*)"));
        put("Internet Explorer", Pattern.compile("(?i)(MSIE\\s(\\d+\\.\\d+))|(Trident).*?((rv:)(\\d+\\.\\d+))"));
    }};

    private static final Pattern OPERATION_SYSTEM_PATTERN = Pattern.compile("\\(([^)]+?)\\)");
    private final static Map<String, List<String>> OPERATION_SYSTEM_MAP = new HashMap<>(Map.of(
            "Linux", List.of("Linux"),
            "Windows", List.of("Windows"),
            "macOS", List.of("Macintosh", "Mac OS X"),
            "Android", List.of("Android"),
            "iOS", List.of("iPhone", "iPad", "iPod"),
            "Chrome OS", List.of("CrOS")
    ));
    String operationSystem;
    String browser;

    public UserAgent(String userAgent) {
        parseOperationSystem(userAgent);
        parseBrowser(userAgent);
    }

    private void parseOperationSystem(String userAgent) {
        if (userAgent != null && !userAgent.isEmpty()) {
            Matcher matcher = OPERATION_SYSTEM_PATTERN.matcher(userAgent);
            if (matcher.find()) {
                String brackets = matcher.group(1);
                for (String k : OPERATION_SYSTEM_MAP.keySet()) {
                    for (String v : OPERATION_SYSTEM_MAP.get(k)) {
                        if (brackets.toLowerCase().contains(v.toLowerCase())) {
                            this.operationSystem = k;
                            return;
                        }
                    }
                }
            }
        }
    }

    private void parseBrowser(String userAgent) {
        Matcher matcher;
        //Ищем браузеры Edge, Chrome, Opera, Firefox, IE по порядку как в мапе
        for (String i : BROWSER_PATTERN_MAP.keySet()) {
            matcher = BROWSER_PATTERN_MAP.get(i).matcher(userAgent);
            if (matcher.find()) {
                this.browser = i;
                return;
            }
        }
        //Ищем браузер Safari. Для него нужны отдельные проверки, что это не Chrome/Edge/Opera, которые содержат Safari
        matcher = BROWSER_SAFARI_PATTERN.matcher(userAgent);
        if (matcher.find()) {
            if (!userAgent.contains("Chrome") && !userAgent.contains("Edge") && !userAgent.contains("OPR")) {
                this.browser = "Safari";
            }
        }
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public String getBrowser() {
        return browser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAgent userAgent = (UserAgent) o;
        return Objects.equals(operationSystem, userAgent.operationSystem) && Objects.equals(browser, userAgent.browser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationSystem, browser);
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "operationSystem='" + operationSystem + '\'' +
                ", browser='" + browser + '\'' +
                '}';
    }
}
