public class Cuckoo extends Bird {
    public void sign() {
        int randomInt = (int) (Math.random() * 10) + 1;
        for (int i = 0; i < randomInt; i++) {
            System.out.println("ку-ку");
        }
    }
}
