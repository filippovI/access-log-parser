import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число: ");
        int x = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число: ");
        int y = new Scanner(System.in).nextInt();
        System.out.println(x + " + " + y + " = " + (x+y));
        System.out.println(x + " - " + y + " = " + (x-y));
        System.out.println(x + " * " + y + " = " + (x*y));
        System.out.println(x + " / " + y + " = " + (double)x/y);
    }
}