public class Circle extends Shape {
    private final Point center;
    private final int radius;

    public Circle(Point center, int radius) {
        if (center == null || radius <= 0)
            throw new IllegalArgumentException("Center = null or radius = 0");
        this.center = center;
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * Math.pow(this.radius, 2);
    }
}
