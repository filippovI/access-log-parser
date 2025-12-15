public class ColorCharacteristic implements PointCharacteristic {
    private final String color;

    public ColorCharacteristic(String color) {
        if (color == null || color.trim().isEmpty()) throw new IllegalArgumentException("color is null or empty");
        this.color = color;
    }

    @Override
    public String describe() {
        return "Color: " + color;
    }

    @Override
    public String toString() {
        return describe();
    }
}
