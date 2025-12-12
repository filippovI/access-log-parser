import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    private final HashSet<String> existingSite;
    private final HashMap<String, Integer> OSCount;

    public Statistics() {
        this.OSCount = new HashMap<>();
        this.existingSite = new HashSet<>();
    }

    public HashSet<String> addEntryExistingSite(HashMap<String, Integer> listSites) {
        for (String siteName : listSites.keySet()) {
            if (!siteName.trim().isEmpty() && listSites.get(siteName) == 200)
                this.existingSite.add(siteName);
        }
        return this.existingSite;
    }

    public HashMap<String, Double> addEntryOSCount(String[] OSName) {
        HashMap<String, Double> OSFrequency = new HashMap<>();
        for (String name : OSName) {
            if (!this.OSCount.containsKey(name)) {
                this.OSCount.put(name, 1);
            } else {
                this.OSCount.put(name, this.OSCount.get(name) + 1);
            }
        }
        for (String key : this.OSCount.keySet()) {
            OSFrequency.put(key, (double) this.OSCount.get(key) / OSName.length);
        }
        return OSFrequency;
    }
}
