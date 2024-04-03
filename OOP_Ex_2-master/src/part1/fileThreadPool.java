package part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class fileThreadPool implements Callable<Integer> {
        private String fileName = "";

        public fileThreadPool(String fileName) {
        this.fileName = fileName;
    }

        @Override
        public Integer call() throws Exception {
        int numOfLines = 0;
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                numOfLines++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return numOfLines;
    }
}
