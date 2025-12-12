import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    private final HashSet<String> existingSites;
    private final HashSet<String> nonExistentSites;
    private final HashMap<String, Integer> OSCountMap;
    private final HashMap<String, Integer> BowserCountMap;

    public Statistics(HashMap<String, Integer> bowserCountMap) {
        this.existingSites = new HashSet<>();
        this.nonExistentSites = new HashSet<>();
        this.OSCountMap = new HashMap<>();
        this.BowserCountMap = new HashMap<>();
    }

    public HashSet<String> addEntryNonExistingSites(HashMap<String, Integer> listSites) {
        for (String siteName : listSites.keySet()) {
            if (!siteName.trim().isEmpty() && listSites.get(siteName) == 404)
                this.nonExistentSites.add(siteName);
        }
        return this.nonExistentSites;
    }

    public HashSet<String> existingSites(HashMap<String, Integer> listSites) {
        for (String siteName : listSites.keySet()) {
            if (!siteName.trim().isEmpty() && listSites.get(siteName) == 200)
                this.existingSites.add(siteName);
        }
        return this.existingSites;
    }

    public HashMap<String, Double> addEntryOSCountMap(String[] OSName) {
        HashMap<String, Double> OSFrequency = new HashMap<>();
        for (String name : OSName) {
            if (!this.OSCountMap.containsKey(name)) {
                this.OSCountMap.put(name, 1);
            } else {
                this.OSCountMap.put(name, this.OSCountMap.get(name) + 1);
            }
        }
        for (String key : this.OSCountMap.keySet()) {
            OSFrequency.put(key, (double) this.OSCountMap.get(key) / OSName.length);
        }
        return OSFrequency;
    }

    public HashMap<String, Double> addEntryBowserCountMap(String[] browserName) {
        HashMap<String, Double> BrowserFrequency = new HashMap<>();
        for (String name : browserName) {
            if (!this.BowserCountMap.containsKey(name)) {
                this.BowserCountMap.put(name, 1);
            } else {
                this.BowserCountMap.put(name, this.BowserCountMap.get(name) + 1);
            }
        }
        for (String key : this.BowserCountMap.keySet()) {
            BrowserFrequency.put(key, (double) this.BowserCountMap.get(key) / browserName.length);
        }
        return BrowserFrequency;
    }
}
