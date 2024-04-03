package part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fileThread extends Thread{
    private String fileName = "";
    private int numOfLines = 0;

    public fileThread(String fileName){
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                numOfLines++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getNumOfLines(){
        return numOfLines;
    }
}
