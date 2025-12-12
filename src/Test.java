import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        PolyLine line = new PolyLine(new ArrayList<>(List.of(
                new Point(3, 4),
                new Point(4, 5),
                new Point(6, 7))));

        ClosedPolyLine closedLine = new ClosedPolyLine(new ArrayList<>(List.of(
                new Point(3, 4),
                new Point(4, 5),
                new Point(3, 4))));

        Measurable[] lineMass = new Measurable[]{line, closedLine};
        printMeasurable1(lineMass);
    }

    public static void printMeasurable1(Measurable[] m) {
        System.out.println(m[0]);
        System.out.println(m[1]);
    }
}

