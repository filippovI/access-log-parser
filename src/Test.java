import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Test {
    public static void main(String[] args) {
        Sparrow sp = new Sparrow();
        Cuckoo cc = new Cuckoo();
        Parrot pt = new Parrot("HEY");
        sp.sign();
        cc.sign();
        pt.sign();
    }
}
