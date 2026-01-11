package ru.courses.main;

import ru.courses.main.exceptions.MaximumLengthException;
import ru.courses.main.log.LogEntry;
import ru.courses.main.log.Statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
                    int sumLine = 0;
                    int maxLength = 0;
                    int minLength = Integer.MAX_VALUE;
                    int yandexBotCount = 0;
                    int googleBotCount = 0;
                    Statistics statistics = new Statistics();
                    while ((line = reader.readLine()) != null) {
                        int length = line.length();
                        if (length > 1024)
                            throw new MaximumLengthException("Длина строки в файле превышает 1024 символа\n" +
                                    "Длина некорректной строки: " + length);
                        LogEntry log = LogEntry.fromString(line);
                        statistics.addEntry(log);
                        String botName = findBrackets(line)
                                .stream()
                                .filter(i -> i.toLowerCase().contains("googlebot") || i.toLowerCase().contains("yandexbot"))
                                .map(String::trim)
                                .map(i -> i.substring(0, i.indexOf("/")))
                                .collect(Collectors.joining());
                        if (botName.equalsIgnoreCase("yandexBot")) yandexBotCount++;
                        if (botName.equalsIgnoreCase("googleBot")) googleBotCount++;


                        if (length > maxLength) maxLength = length;
                        if (length < minLength) minLength = length;
                        sumLine++;
                    }
                    if (sumLine > 0) {
                        System.out.printf("Общее количество строк в файле: %s\n", sumLine);
                        System.out.println("Количество запросов от yandexBot относительно общего числа запросов: "
                                + (double) yandexBotCount / sumLine);
                        System.out.println("Количество запросов от googleBot относительно общего числа запросов: "
                                + (double) googleBotCount / sumLine);
                        System.out.println("Все существующие страницы сайта: " + statistics.getExistSites());
                        System.out.println("Статистика операционных систем: " + statistics.getOperationSystemsFrequency());
                    } else System.out.println("Файл пустой");
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

    public static List<String> findBrackets(String line) {
        List<String> brackets = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\((.*?)\\)"); //
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            brackets.addAll(Arrays.asList(matcher.group(1).split(";")));
        }
        return brackets;
    }
}