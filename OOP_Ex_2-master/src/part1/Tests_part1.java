package part1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * In the tests class we checked only small number of files since the method creates only one line for each file.
 * Therefore, the large numbers we checked in the main class by using the compareTime method
 */


    class Tests_part1 {

        private String[] fileNames1;

        @Test
        void createTextFiles() throws IOException {
            fileNames1 = Ex2_1.createTextFiles(1090, 1000, 100);
            String currName = "";
            for (int i=1; i <= fileNames1.length; i++){
                currName = "file_" + i + ".txt";
                assertEquals(fileNames1[i-1], currName);
            }
            assertEquals(1090, fileNames1.length);
            Ex2_1.cleanUp(fileNames1);

            // negative number of files
            assertThrows(NegativeArraySizeException.class, ()-> {
                Ex2_1.createTextFiles(-27687, 2344, 2342535);
            });
        }
        @Test
        void getNumOfLines() throws IOException {
            fileNames1=Ex2_1.createTextFiles(59,14,100);
            assertEquals(59,Ex2_1.getNumOfLines(fileNames1));
        }
        @Test
        void getNumOfLinesThreads() throws IOException {
            Ex2_1 ex2_1 = new Ex2_1();
            fileNames1=Ex2_1.createTextFiles(9,14,100);
            assertEquals(9,ex2_1.getNumOfLinesThreads(fileNames1));
            fileNames1=Ex2_1.createTextFiles(9,14,300);
            assertEquals(9,ex2_1.getNumOfLinesThreads(fileNames1));
        }
        @Test
        void getNumOfLinesThreadPool() throws IOException {
            Ex2_1 ex2_1 = new Ex2_1();
            fileNames1=Ex2_1.createTextFiles(60,14,100);
            assertEquals(60,ex2_1.getNumOfLinesThreadPool(fileNames1));
            fileNames1=Ex2_1.createTextFiles(16,14,300);
            assertEquals(16, ex2_1.getNumOfLinesThreadPool(fileNames1));
        }

        @AfterEach
        void clean_up(){
            Ex2_1.cleanUp(fileNames1);
        }

    }
