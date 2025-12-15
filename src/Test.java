import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Point p = new Point(3,4);
        Circle cl = new Circle(p, 4);
        Quadrate qd = new Quadrate(p, 0);
        Rectangle rc = new Rectangle(p, 4,5);
        System.out.println(cl.getArea());
        System.out.println(qd.getArea());
        System.out.println(rc.getArea());
    }
}
