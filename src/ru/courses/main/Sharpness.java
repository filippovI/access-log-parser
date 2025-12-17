package ru.courses.main;

public enum Sharpness {
    NOT_HOT("не острый"),
    HOT("острый"),
    VERY_HOT("очень острый");

    private final String text;

    Sharpness(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
