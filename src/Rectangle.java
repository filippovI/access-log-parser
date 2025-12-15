public class Rectangle extends Shape {
    private final Point leftUpCorner;
    private final int sideLengthOne;
    private final int sideLengthTwo;

    public Rectangle(Point leftUpCorner, int sideLengthOne, int sideLengthTwo) {
        if (leftUpCorner == null || sideLengthOne <= 0 || sideLengthTwo <= 0)
            throw new IllegalArgumentException("Starting point = null or one of the parties <= 0");
        this.leftUpCorner = leftUpCorner;
        this.sideLengthOne = sideLengthOne;
        this.sideLengthTwo = sideLengthTwo;
    }

    @Override
    public double getArea() {
        return this.sideLengthOne * sideLengthTwo;
    }
}
