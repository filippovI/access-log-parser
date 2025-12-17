package ru.courses.main;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int fileCount = 0;
        while (true) {
            System.out.println("Введите путь к файлу: ");
            File file = new File(new Scanner(System.in).nextLine().trim());
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            //Проверяем, что это файл
            if (fileExists && !isDirectory) {
                fileCount++;
                System.out.println("Путь указан верно\nЭто файл номер " + fileCount);
                //Если не файл, проверяем, что это директория
            } else if (isDirectory)
                System.out.println("Это путь к директории");
                //Если не файл и не директория, выводим уведомление об этом
            else
                System.out.println("Такого файла или директории не существует");
            System.out.println("----------------------------------------");
        }
    }
}