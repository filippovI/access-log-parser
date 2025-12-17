package ru.courses.main;

public class Sauce {
    private final String name;
    private final Sharpness sharpness;

    public Sauce(String name, Sharpness sharpness) {
        if (name.trim().isEmpty() || sharpness == null) throw new IllegalArgumentException("name or sharpness is null");
        this.name = name;
        this.sharpness = sharpness;
    }

    @Override
    public String toString() {
        return String.format("Соус: %s\nОстрота: %s", this.name, sharpness);
    }
}
