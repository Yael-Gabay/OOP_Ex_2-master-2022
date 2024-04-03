package part2;

import java.util.concurrent.*;

public class CustomExecutor <T> extends ThreadPoolExecutor{
    private int[] arrPriorities = {0, 0, 0};
    //index- represents the priority-1,
    //arrPriorities[i]- represents the number of tasks with i+1 priority
    private static final int MIN_NUM_OF_THREADS = Runtime.getRuntime().availableProcessors()/2;
    private static final int MAX_NUM_OF_THREADS = Runtime.getRuntime().availableProcessors()-1;

    //constructor
    public CustomExecutor() {
        super(MIN_NUM_OF_THREADS,   //minimum number of threads to allow in the pool
                MAX_NUM_OF_THREADS, //maximum number of threads to allow in the pool
                300,    //maximum time that excess idle threads will wait
                TimeUnit.MILLISECONDS,  //timeunit
                new PriorityBlockingQueue<>()); //priority queue which sets the objects order according to their priority
    }

    /**
     * Creates a new future task with a comparable method
     * @param task object
     * @param <T> generic type
     * @return an upgraded future task with a compare method
     */
    protected <T> MyFutureTask<T> newTask(Task task) {
        return new MyFutureTask<T>(task.getCall(), task.getPriority());
    }

    /**
     * Creates a Future Task object from the given task and submits it to the priority queue
     * @param task Task object
     * @param <T> generic type
     * @return Future Task with comparable method
     */
    public <T> Future<T> submit(Task<T> task) {
        if (task == null){
            throw new NullPointerException();
        }
//        RunnableFuture<T> futureTask = newTaskFor(task);
        MyFutureTask<T> futureTask = newTask(task);
        if (task.getPriority()>0)
            arrPriorities[task.getPriority()-1]++;
        execute(futureTask);
        return futureTask;

    }

    /**
     * Creates a new Task object and sends it to the submit method inorder to create a future task for it
     * @param callable
     * @param <T> generic type
     * @return Future Task with comparable method
     */
    public <T> Future<T> submit(Callable<T> callable){
        Task<T> task = Task.createTask(callable);
        return submit(task);
    }

    /**
     Creates a new Task object and sends it to the submit method inorder to create a future task for it
     * @param callable
     * @param <T> generic type
     * @return Future Task with comparable method
     */
    public <T> Future<T> submit(Callable<T> callable, TaskType type) {
        Task<T> task = Task.createTask(callable, type);
        return submit(task);
    }


    /**
     * Returns the max priority in queue in O(1) complexity
     * @return 1 or 2 or 3- the max priority in the queue.
     * 1 is the highest priority
     */
    public int getCurrentMax(){
        try {
            if (arrPriorities[0] > 0)
                return 1;
            if (arrPriorities[1] > 0)
                return 2;
            if (arrPriorities[2] > 0)
                return 3;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * After Execute update the number of tasks of each priority
     * @param runnable
     * @param throwable
     */
    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {
        super.afterExecute(runnable, throwable);
        if (getCurrentMax() > 0){
            arrPriorities[getCurrentMax()-1]--;
        }
    }

    /**
     * Terminates a CustomExecutor object (upgraded thread pool) in a way that-
     *      Does not allow entry of additional tasks to the queue
     *      Completes all tasks that remained in the queue
     *      Terminates all tasks currently in progress in the CustomExecutor threads collection
     * @throws InterruptedException if exists
     */
    public void gracefullyTerminate() throws InterruptedException {
         try{
             awaitTermination(300, TimeUnit.MILLISECONDS);
             super.shutdown();
         }catch (InterruptedException e){
             System.out.println("Got InterruptedException: "+e.getMessage());
         }
    }
}
