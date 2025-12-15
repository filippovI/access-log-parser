public class Test {
    public static void main(String[] args) {
        Student Ivan = new Student("Ivan", i -> i % 2 == 0 && i >= 1 && i <= 100000);
        Ivan.addGrade(1);
        Ivan.addGrade(2);
        Ivan.addGrade(4);
        Ivan.addGrade(100000);
        Ivan.addGrade(100001);
        System.out.println(Ivan);
    }
}
