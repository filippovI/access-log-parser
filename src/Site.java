import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Site {
    private final Map<String, Integer> pagesStatus;
    private final List<String> usersOS;
    private final List<String> usersBrowsers;

    public Site() {
        this(new HashMap<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Site(int status, String page, String os, String browser) {
        this(new HashMap<>() {{
                 put(page, status);
             }},
                new ArrayList<>(List.of(os)),
                new ArrayList<>(List.of(browser)));
    }

    public Site(HashMap<String, Integer> pagesStatus, ArrayList<String> os, ArrayList<String> usersBrowsers) {
        this.pagesStatus = pagesStatus.entrySet().stream()
                .filter(i -> !i.getKey().trim().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.usersOS = os.stream()
                .filter(i -> !i.trim().isEmpty())
                .collect(Collectors.toList());
        this.usersBrowsers = usersBrowsers.stream()
                .filter(i -> !i.trim().isEmpty())
                .collect(Collectors.toList());
    }

    public void addEntryUsersBrowsers(String browser) {
        addEntryUsersBrowsers(new ArrayList<>(List.of(browser)));
    }

    public void addEntryUsersBrowsers(ArrayList<String> browser) {
        browser.forEach(i -> {
            if (!i.trim().isEmpty()) {
                this.usersBrowsers.add(i);
            }
        });
    }

    public void addEntryUsersOS(String OS) {
        addEntryUsersOS(new ArrayList<>(List.of(OS)));
    }

    public void addEntryUsersOS(ArrayList<String> OS) {
        OS.forEach(os -> {
            if (!os.trim().isEmpty()) {
                this.usersOS.add(os);
            }
        });
    }

    public void addEntryPagesStatus(int status, String page) {
        addEntryPagesStatus(new HashMap<>() {{
            put(page, status);
        }});
    }

    public void addEntryPagesStatus(HashMap<String, Integer> pagesStatus) {
        pagesStatus.forEach((k, v) -> {
            if (!k.trim().isEmpty()) {
                this.pagesStatus.put(k, v);
            }
        });
    }

    public Map<String, Integer> getPagesStatus() {
        return pagesStatus;
    }

    public List<String> getUsersOS() {
        return this.usersOS;
    }

    public List<String> getUsersBrowsers() {
        return this.usersBrowsers;
    }
}