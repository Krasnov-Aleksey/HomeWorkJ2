/*
*Получить исходную json строку из файла, используя FileReader или Scanner
Дана json строка вида:
String json = "[{\"фамилия\":\"Иванов\",\"оценка\":\"5\",\"предмет\":\"Математика\"}," +
"{\"фамилия\":\"Петрова\",\"оценка\":\"4\",\"предмет\":\"Информатика\"}," +
"{\"фамилия\":\"Краснов\",\"оценка\":\"5\",\"предмет\":\"Физика\"}]";

Задача написать метод(ы), который распарсить строку и выдаст ответ вида:
Студент Иванов получил 5 по предмету Математика.
Студент Петрова получил 4 по предмету Информатика.
Студент Краснов получил 5 по предмету Физика.

Используйте StringBuilder для подготовки ответа. Далее создайте метод, который запишет
результат работы в файл. Обработайте исключения и запишите ошибки в лог файл с помощью Logger.

 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Task1 {
    static Logger logger;
    public static void main(String[] args) {
        String json1 = "[{\"фамилия\":\"Иванов\",\"оценка\":\"5\",\"предмет\":\"Математика\"}," +
                "{\"фамилия\":\"Петрова\",\"оценка\":\"4\",\"предмет\":\"Информатика\"}," +
                "{\"фамилия\":\"Краснов\",\"оценка\":\"5\",\"предмет\":\"Физика\"}]";

        String path = "src/text.txt";
        String pathw = "src/textw.txt";

        createLogger();

        String json = readFile (path);
        json=jsonArray(json);
        writeFile(json, pathw);

        closeLogger();
    }

    static String jsonArray(String json) {
        // Удаляем [,],{,},", запятую заменяем на :
        String temp = json.replace("[", "");
        json = temp;
        temp = json.replace("]", "");
        json = temp;
        temp = json.replace("{", "");
        json = temp;
        temp = json.replace("}", "");
        json = temp;
        temp = json.replace("\"", "");
        json = temp;
        temp = json.replace("\\", "");
        json = temp;
        temp = json.replace(",", ":");
        json = temp;

        String[] jsonArr = json.split(":"); // Массив строк по :

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 1; i < jsonArr.length-4; i = i + 6) {
            stringBuilder.append("Студент ");
            stringBuilder.append(jsonArr[i]);
            stringBuilder.append(" получил ");
            stringBuilder.append(jsonArr[i + 2]);
            stringBuilder.append(" по предмету ");
            stringBuilder.append(jsonArr[i + 4]);
            stringBuilder.append("\n");
            }
            String resJson = stringBuilder.toString();
            System.out.println(resJson);
            return resJson;
    }
    static String readFile (String path) {
        File file = new File(path);
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine());
            }
            logger.info("Фаил прочитан");
        } catch (Exception e) {
            e.printStackTrace();
            logger.warning("Фаил не прочитан");
        }
        return stringBuilder.toString();
    }
    static void writeFile(String json, String pathw) {
        try (FileWriter fileWriter = new FileWriter(pathw)) {
            fileWriter.write(json);
            fileWriter.flush();
            logger.info("Фаил записан");
        } catch (Exception e) {
            e.printStackTrace();
            logger.warning("Фаил не записан");
        }
    }
    private static void createLogger() {
        logger = Logger.getAnonymousLogger();
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler ("src/log.txt", true);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SimpleFormatter formatter = new SimpleFormatter();
        if (fileHandler != null){
            fileHandler.setFormatter(formatter);
        }
    }
    private static void closeLogger() {
        for (Handler handler: logger.getHandlers()){
            handler.close();
        }
    }

}
