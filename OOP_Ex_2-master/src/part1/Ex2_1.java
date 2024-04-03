package part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Ex2_1 {
    public static void main(String[] args) throws IOException {

//        String[] fileNames = createTextFiles(10, 1000, 100);
//        for (String fileName : fileNames) {
//            System.out.println(fileName);
//        }
//        System.out.println("Total num of lines: "+getNumOfLinesThreadPool(fileNames));
//        cleanUp(fileNames);

        Ex2_1.compareTime();
    }

    /**
     * @param n     number of files to generate
     * @param seed  seed for random
     * @param bound bound for random number generator
     * @return fileNames returns a String array contains the created files names
     */
    public static String[] createTextFiles(int n, int seed, int bound) throws IOException {
        String[] fileNames = new String[n];
        Random random = new Random(seed);
        //create n files
        for (int i = 1; i <= n; i++) {
            fileNames[i-1] = "file_" + i + ".txt";
            try (FileWriter writer = new FileWriter(fileNames[i-1])) {
                //write random number of lines with at least 10 characters in each row
                for (int j = 0; j < random.nextInt(bound) + 10; j++) {
                    writer.write(random.nextInt());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return fileNames;
    }

    /**
     * @param fileNames array of file names
     * Deletes the files
     */
    public static void cleanUp(String[] fileNames) {
        for (String fileName : fileNames) {
            java.io.File file = new java.io.File(fileName);
            file.delete();
        }
    }

    /**
     * Reads all files and count the number of rows in each file
     * @param fileNames array (string type) of file names
     * @return the sum of all files number of lines
     */
    public static int getNumOfLines(String[] fileNames){
        int numOfLines = 0;
        for (String fileName : fileNames) {
            try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                numOfLines++;
            }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return numOfLines;
    }

    /**
     * Counts the number of lines in all files using threads
     * @param fileNames array (string type) of file names
     * @return the number of lines in all files
     */
    public int getNumOfLinesThreads(String[] fileNames){
        fileThread[] threads = new fileThread[fileNames.length]; //create threads array in the same size as the number of files
        for (int i = 0; i < fileNames.length; i++) {
            threads[i] = new fileThread(fileNames[i]); //create a thread for each file
            threads[i].start(); //start thread
        }
        int totalNumOfLines = 0; //count the number of lines
        for (fileThread thread : threads) {
            try {
                thread.join(); //wait until the thread finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            totalNumOfLines += thread.getNumOfLines(); //add the number of lines in the file to the total
        }
        return totalNumOfLines;
    }


    /**
     * Counts the number of lines in all files using ExecutorService
     * @param fileNames array of file names
     * @return the number of lines in the files
     */
    public int getNumOfLinesThreadPool(String[] fileNames) {
        //count the number of lines in the files using thread pool
        AtomicInteger count = new AtomicInteger(0);//counter for the number of lines
        //create a thread pool with the size of the files array
        ExecutorService executorService = Executors.newFixedThreadPool(10); //It's inefficient, it is better to make a much smaller number of threads
        for (String fileName : fileNames) {
            executorService.execute(() -> {
                try (Scanner scanner = new Scanner(new File(fileName))) {
                    while (scanner.hasNextLine()) {
                        scanner.nextLine();
                        count.getAndIncrement();//add 1 to the count
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        //shut down the thread pool after all tasks have been submitted
        executorService.shutdown();
        //block the current thread until all tasks have completed execution
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count.get();
        }


    /**
     * Compares the methods time performance and if the 3 methods return the same number of lines
     */
    public static void compareTime() throws IOException {
        Ex2_1 ex2_1 = new Ex2_1();
        String[] fileNames = createTextFiles(1, 40, 65376);
        System.out.println("n = 1, seed = 42, bound = 65376\n");
        long start, end;

        System.out.println("Without Threads-");
        start = System.currentTimeMillis();
        int linesNum = getNumOfLines(fileNames);
        System.out.println("num of lines: "+linesNum);
        end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + "ms\n");

        System.out.println("Using Threads-");
        start = System.currentTimeMillis();
        int threadLinesNum = ex2_1.getNumOfLinesThreads(fileNames);
        System.out.println("num of lines: "+threadLinesNum);
        end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + "ms\n");

        System.out.println("Using ThreadPool-");
        start = System.currentTimeMillis();
        int threadPoolLinesNum = ex2_1.getNumOfLinesThreadPool(fileNames);
        System.out.println("num of lines: "+threadPoolLinesNum);
        end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + "ms\n");

        if (linesNum == threadLinesNum)
            if (threadLinesNum == threadPoolLinesNum)
                System.out.println("Well done!");
            else
                System.out.println("Oops, it looks like we have a problem");;

        cleanUp(fileNames);
    }
}
