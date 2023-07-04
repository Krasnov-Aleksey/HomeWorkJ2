import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.*;

/*
 *Реализуйте алгоритм сортировки пузырьком числового массива, результат после каждой итерации запишите в лог-файл
 */
public class Task2 {
    public static void main(String[] args) {
        int newArr [] = rndArray(5,0,10);
        System.out.println(Arrays.toString(newArr));
        int sortArr[] = bubbleSort(newArr);
        System.out.println(Arrays.toString(sortArr));
    }

    static int [] rndArray (int count, int min, int max) {
        // Случайный массим длина мин мак
        Random random = new Random();
        int [] arr = new int [count];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(min, max);
        }
        return arr;
    }
    static int [] bubbleSort (int [] arr) {
        // Пузырьковая сортировка, логирование

        Logger logger = Logger.getLogger(Task2.class.getName());
        FileHandler fh = null;
        try {
            fh = new FileHandler("src/log1.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.addHandler(fh);
        SimpleFormatter sFormat = new SimpleFormatter();

        fh.setFormatter(sFormat);

        for (int i = 0; i < arr.length ; i++) {
            for (int j = 0; j < arr.length ; j++) {
                if (arr[i]<arr[j]) {
                    int temp = arr [j];
                    arr[j] = arr [i];
                    arr[i]=temp;
                    logger.info(Arrays.toString(arr));
                }
            }
        }
        return  arr;
    }
}
