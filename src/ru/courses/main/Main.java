package ru.courses.main;

import ru.courses.main.exceptions.MaximumLengthException;
import ru.courses.main.log.LogEntry;
import ru.courses.main.log.Statistics;

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
            //Проверяем, что это файл
            if (file.exists() && !file.isDirectory()) {
                fileCount++;
                System.out.println("Путь указан верно\nЭто файл номер " + fileCount);
                try {
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader);
                    Statistics stat = new Statistics();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.length() > 1024)
                            throw new MaximumLengthException("Длина строки в файле превышает 1024 символа\n" +
                                    "Длина некорректной строки: " + line.length());
                        //Основная логика
                        LogEntry logEntry = LogEntry.fromString(line);
                        stat.addEntry(logEntry);
                    }
                    System.out.println("Средний объем трафика за час: " + stat.getTrafficRate());
                    System.out.println("Статистика браузеров: " + stat.getBrowsersFrequency());
                    System.out.println("Статистика операционных систем: " + stat.getOperationSystemsFrequency());
                    System.out.println("Среднее количество посещений за час: " + stat.getAverageOfVisitsPerHour());
                    System.out.println("Среднее количество ошибочных запросов за час: " + stat.getAverageErrorRequestsPerHour());
                    System.out.println("Средний трафик одним пользователем: " + stat.getAverageTrafficPerUser());
                    System.out.println("Пиковая посещаемость в секунду: " + stat.getPeakVisitPerSecond());
                    System.out.println("Сайты с ссылками на текущий сайт: " + stat.getRefererSet());
                    System.out.println("Максимальная посещаемость одним пользователем: " + stat.getCountMaximumVisitsByOneUser());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //Если не файл, проверяем, что это директория
            } else if (file.isDirectory())
                System.out.println("Это путь к директории");
                //Если не файл и не директория, выводим уведомление об этом
            else
                System.out.println("Такого файла или директории не существует");
            System.out.println("----------------------------------------");
        }

    }
}