package ru.courses.main;

public class Test {
    public static void main(String[] args) throws IllegalAccessException {
        Sauce sauce = new Sauce("Кисло-сладкий", Sharpness.NOT_HOT);
        Sauce sauce1 = new Sauce("Горчица", Sharpness.HOT);
        Sauce sauce2 = new Sauce("Табаско", Sharpness.VERY_HOT);
        System.out.println(sauce);
        System.out.println(sauce1);
        System.out.println(sauce2);
    }
}
