public class TimeCharacteristic implements PointCharacteristic {
    private final String time;

    public TimeCharacteristic(String time) {
        if (time == null || time.trim().isEmpty()) throw new IllegalArgumentException("time is null or empty");
        this.time = time;
    }

    @Override
    public String describe() {
        return "time: " + this.time;
    }

    @Override
    public String toString() {
        return describe();
    }
}
