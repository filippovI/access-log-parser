import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Test {
    public static void main(String[] args) {
        Pointt pt = new Pointt.PointtBuilder(2)
                .setY(3)
                .setZ(5)
                .setColor("Green")
                .setTime("12:00")
                .build();
        System.out.println(pt.getCharacteristics());

    }
}
