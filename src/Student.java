import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String name;
    private final List<Integer> grades;

    public Student(String name) throws IllegalAccessException {
        this(name, List.of());
    }

    public Student(String name, List<Integer> grades) throws IllegalAccessException {
        if (name.isEmpty()) throw new IllegalAccessException("the name must not be empty");
        for (int i : grades)
            if (i < 2 || i > 5) throw new IllegalAccessException("grade must be between 2 and 5");
        this.name = name;
        this.grades = new ArrayList<>(grades);
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public void addGrade(int grade) throws IllegalAccessException {
        addGrades(List.of(grade));
    }

    public void addGrades(List<Integer> grades) throws IllegalAccessException {
        for (int i : grades)
            if (i < 2 || i > 5) throw new IllegalAccessException("grade must be between 2 and 5");
        this.grades.addAll(grades);
    }

    @Override
    public String toString() {
        return this.name + ": " + this.grades;
    }
}