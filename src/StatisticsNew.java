import java.util.HashMap;
import java.util.HashSet;

public class StatisticsNew {
    private final HashSet<String> existingSites;
    private final HashSet<String> nonExistentSites;
    private final HashMap<String, Integer> OSCountMap;
    private final HashMap<String, Integer> BowserCountMap;
    private final Site site;


    public StatisticsNew(Site site) {
        this.existingSites = new HashSet<>();
        this.nonExistentSites = new HashSet<>();
        this.OSCountMap = new HashMap<>();
        this.BowserCountMap = new HashMap<>();
        this.site = site;
    }

    public HashSet<String> addEntryNonExistingSites() {
        for (String siteName : site.getPagesStatus().keySet()) {
            if (!siteName.trim().isEmpty() && site.getPagesStatus().get(siteName) == 404)
                this.nonExistentSites.add(siteName);
        }
        return this.nonExistentSites;
    }

    public HashSet<String> addEntryExistingSites() {
        for (String siteName : site.getPagesStatus().keySet()) {
            if (!siteName.trim().isEmpty() && site.getPagesStatus().get(siteName) == 200)
                this.existingSites.add(siteName);
        }
        return this.existingSites;
    }

    public HashMap<String, Double> addEntryOSCountMap() {
        HashMap<String, Double> OSFrequency = new HashMap<>();
        for (String name : site.getUsersOS()) {
            if (!this.OSCountMap.containsKey(name)) {
                this.OSCountMap.put(name, 1);
            } else {
                this.OSCountMap.put(name, this.OSCountMap.get(name) + 1);
            }
        }
        for (String key : this.OSCountMap.keySet()) {
            OSFrequency.put(key, (double) this.OSCountMap.get(key) / site.getUsersOS().size());
        }
        return OSFrequency;
    }

    public HashMap<String, Double> addEntryBowserCountMap() {
        HashMap<String, Double> BrowserFrequency = new HashMap<>();
        for (String name : site.getUsersBrowsers()) {
            if (!this.BowserCountMap.containsKey(name)) {
                this.BowserCountMap.put(name, 1);
            } else {
                this.BowserCountMap.put(name, this.BowserCountMap.get(name) + 1);
            }
        }
        for (String key : this.BowserCountMap.keySet()) {
            BrowserFrequency.put(key, (double) this.BowserCountMap.get(key) / site.getUsersBrowsers().size());
        }
        return BrowserFrequency;
    }
}