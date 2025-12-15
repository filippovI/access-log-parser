import java.util.ArrayList;
import java.util.List;

public final class Student {
    private final List<Integer> grades;
    private final String name;
    private final RuleForStudentGrades<Integer> gradeValidator;

    public Student(String name, RuleForStudentGrades<Integer> gradeValidator) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("name is null or empty");
        this.name = name;
        this.grades = new ArrayList<>();
        this.gradeValidator = (gradeValidator != null) ? gradeValidator : (grade -> grade >= 2 && grade <= 5);
    }

    public Student(String name) {
        this(name, null);
    }

    public void addGrade(int grade) {
        if (gradeValidator.check(grade)) {
            grades.add(grade);
        } else {
            System.out.println("Оценка " + grade + " не соответствует правилу для студента " + name + " и не была добавлена.");
        }
    }

    @Override
    public String toString() {
        return "Student{" + "name='" + name + "', grades=" + grades + '}';
    }
}