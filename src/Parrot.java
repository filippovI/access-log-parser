public class Parrot extends Bird {
    final String text;

    public Parrot(String text) {
        if (text == null || text.trim().isEmpty()) throw new IllegalArgumentException("text must be not null and empty");
        this.text = text;
    }

    public void sign() {
        int cut = (int) (Math.random() * this.text.length()) + 1;
        System.out.println(this.text.substring(0, cut));
    }
}
