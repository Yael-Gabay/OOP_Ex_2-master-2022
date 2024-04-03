package part2;

import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import java.util.concurrent.*;
import static java.lang.Thread.sleep;

/**
* The following tests class was given with the assignment
*/

public class Tests_part2{
    public static final Logger logger = LoggerFactory.getLogger(Test.class);
    /**
     * check if the queue add by priority,
     * set core and max to be 1, because we need
     * that task get in the workqueue.
     *
     * Print - print the priorities og the queue's tasks
     * by the order in the queue
     */
    @Test
    public void partialTest() throws InterruptedException {
        CustomExecutor customExecutor = new CustomExecutor();
        customExecutor.setCorePoolSize(1);
        customExecutor.setMaximumPoolSize(1);
        for (int i = 0; i < 5; i++) {
            Callable<String> testIO = () -> {
                StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                return sb.reverse().toString();
            };

            var reverseTask1 = customExecutor.submit(testIO, TaskType.IO);
            var task = Task.createTask(()->{
                int sum = 0;
                for (int j = 1; j <= 10; j++) {
                    sum += j;
                }
                return sum;
            }, TaskType.COMPUTATIONAL);
            var sumTask = customExecutor.submit(task);
            var testMath = customExecutor.submit(() -> {
                return 1000 * Math.pow(1.02, 5);
            }, TaskType.COMPUTATIONAL);

            Callable<String> testIO2 = () -> {
                StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                return sb.reverse().toString();
            };
            var testt = customExecutor.submit(testIO2, TaskType.IO);
            System.out.println(customExecutor.getQueue().toString());
            customExecutor.getCurrentMax();
            final String get1;
            final double get2;
            final int get3;
            try {
                get1 = (String) testt.get();
                get2 = (double) testMath.get();
                get3 = (int) sumTask.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            logger.info(()-> "Reversed String = " + get1);
            logger.info(()->String.valueOf("Total Price = " + get2));
            logger.info(()-> "Current maximum priority = " +
                    customExecutor.getCurrentMax());
        }
        customExecutor.gracefullyTerminate();
    }
}
