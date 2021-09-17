import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class solveFileTasks {

    public static void main(String[] args) {

        String pathToFile = "C:\\Users\\kbaib\\Downloads\\dataset_91069.txt";
        File file = new File(pathToFile);

        long population = 0;
        long result = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String num = scanner.nextLine();
                String[] data = num.split("\\t");
                String populationNum = data[1].replaceAll(",", "");
                population = Long.parseLong(populationNum);
                if (population > result) {
                  result = population;
                } else {
                    population = population;
                }
            }
            System.out.println(population);
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + pathToFile);
        }
    }
}
