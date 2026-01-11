package ru.courses.main.log;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.courses.main.patterns.PatternsForLogParsing.*;

public class UserAgent {

    //Нужно сохранить порядок
    private static final Map<String, Pattern> BROWSER_PATTERN_MAP = new LinkedHashMap<>() {{
        put("Edge", Pattern.compile("(?i)Edge/(\\d+(\\.\\d+)*)"));
        put("Opera", Pattern.compile("(?i)(Opera|OPR)/(\\d+(\\.\\d+)*)"));
        put("Chrome", Pattern.compile("(?i)Chrome/(\\d+(\\.\\d+)*)"));
        put("Firefox", Pattern.compile("(?i)Firefox/(\\d+(\\.\\d+)*)"));
        put("Internet Explorer", Pattern.compile("(?i)(MSIE\\s(\\d+\\.\\d+))|(Trident).*?((rv:)(\\d+\\.\\d+))"));
    }};
    private final static Map<String, List<String>> OPERATION_SYSTEMS_MAP = new HashMap<>(Map.of(
            "Linux", List.of("Linux"),
            "Windows", List.of("Windows"),
            "macOS", List.of("Macintosh"),
            "Android", List.of("Android"),
            "iOS", List.of("iPhone", "iPad", "iPod"),
            "Chrome OS", List.of("CrOS")
    ));
    private final String operationSystem;
    private final String browser;
    private final boolean isBot;

    private UserAgent(String operationSystem, String browser, boolean isBot) {
        this.operationSystem = operationSystem;
        this.browser = browser;
        this.isBot = isBot;
    }

    public static UserAgent fromString(String userAgent) {
        return new UserAgent(parseOperationSystem(userAgent), parseBrowser(userAgent), parseBot(userAgent));
    }


    private static String parseOperationSystem(String userAgent) {
        if (userAgent != null && !userAgent.isEmpty()) {
            Matcher matcher = OPERATION_SYSTEM_PATTERN.matcher(userAgent);
            if (matcher.find()) {
                String brackets = matcher.group(1);
                for (String k : OPERATION_SYSTEMS_MAP.keySet()) {
                    for (String v : OPERATION_SYSTEMS_MAP.get(k)) {
                        if (brackets.toLowerCase().contains(v.toLowerCase())) {
                            return k;
                        }
                    }
                }
            }
        }
        return "";
    }

    private static String parseBrowser(String userAgent) {
        Matcher matcher;
        //Ищем браузеры Edge, Chrome, Opera, Firefox, IE по порядку как в мапе
        for (String i : BROWSER_PATTERN_MAP.keySet()) {
            matcher = BROWSER_PATTERN_MAP.get(i).matcher(userAgent);
            if (matcher.find()) {
                return i;
            }
        }
        //Ищем браузер Safari. Для него нужны отдельные проверки, что это не Chrome/Edge/Opera, которые содержат Safari
        matcher = BROWSER_SAFARI_PATTERN.matcher(userAgent);
        if (matcher.find()) {
            if (!userAgent.contains("Chrome") && !userAgent.contains("Edge") && !userAgent.contains("OPR")) {
                return "Safari";
            }
        }
        return "";
    }

    private static boolean parseBot(String userAgent) {
        if (userAgent != null && !userAgent.isEmpty()) {
            Matcher matcher = IS_BOT_PATTERN.matcher(userAgent);
            if (matcher.find()) {
                String lastBrackets = matcher.group(1);
                return lastBrackets.contains("bot");
            }
        }
        return false;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public String getBrowser() {
        return browser;
    }

    public boolean isBot() {
        return isBot;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "operationSystem='" + operationSystem + '\'' +
                ", browser='" + browser + '\'' +
                ", isBot=" + isBot +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserAgent userAgent = (UserAgent) o;
        return isBot == userAgent.isBot
                && Objects.equals(operationSystem, userAgent.operationSystem)
                && Objects.equals(browser, userAgent.browser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationSystem, browser, isBot);
    }

}
