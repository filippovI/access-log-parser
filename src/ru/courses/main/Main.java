package ru.courses.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int fileCount = 0;
        String path;
        while (true) {
            System.out.println("Введите путь к файлу: ");
            path = new Scanner(System.in).nextLine().trim();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            //Проверяем, что это файл
            if (fileExists && !isDirectory) {
                fileCount++;
                System.out.println("Путь указан верно\nЭто файл номер " + fileCount);
                try {
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader);
                    String line;
                    int sumLineLength = 0;
                    int maxLength = 0;
                    int minLength = Integer.MAX_VALUE;
                    while ((line = reader.readLine()) != null) {
                        int length = line.length();
                        if (length > 1024)
                            throw new MaximumLengthException("Длина строки в файле превышает 1024 символа\n" +
                                    "Длина некорректной строки: " + length);
                        if (length > maxLength) maxLength = length;
                        if (length < minLength) minLength = length;
                        sumLineLength += length;
                    }
                    if (sumLineLength > 0)
                        System.out.printf("Общее количество строк в файле: %s\n" +
                            "Длина самой длинной строки: %s\n" +
                            "Длина самой короткой строки: %s\n", sumLineLength, maxLength, minLength);
                    else System.out.println("Файл пустой");
                    // code here
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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