package part2;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Task<T> extends FutureTask <T>implements Callable<T>, Comparable<Task<T>> {
    private Callable<T> task;
    private TaskType type;

    //constructor method
    private Task(Callable<T> task, TaskType type) {
        super(task);
        this.task = task;
        this.type = type;
    }

    /**
     * Creates a new Task object which has a specified TaskType
     * Using Factory method design pattern
     * @param task Callable object
     * @param type a TaskType object
     * @param <T> generic type
     * @return a new Task object
     */
    public static <T> Task<T> createTask(Callable<T> task, TaskType type) {
        return new Task<T>(task, type);
    }

    /**
     * Creates a new Task object with the OTHER type- unknown type
     * using the 2 inputs constructor
     * @param task Callable object
     * @param <T> generic type
     * @return a new Task object
     */
    public static <T> Task<T> createTask(Callable<T> task) {
        return createTask(task, TaskType.OTHER);
    }


    public TaskType getTaskType(){
        return type;
    }

    public void setTaskType(TaskType other){
        this.type = other;
    }

    /**
     * Returns the taskType's priority
     * @return taskType's priority
     */
    public int getPriority() {
        return type.getPriorityValue();
    }

    /**
     * @param other a Task object
     * @return 0, if the priorities are equal
     *         1, if this object has a greater priority
     *        -1, if the given object has a greater priority
     */
    @Override
    public int compareTo(Task<T> other) {
        int diff = getPriority() - other.getPriority();
        if (diff < 0)
            return 1;
        else if (diff > 0)
            return -1;
        else
            return 0;
    }


    /**
     * Runs the Callable- call method
     * @return call method
     * @throws Exception if exists
     */
    @Override
    public T call() throws Exception {
        return task.call();
    }


    @Override
    public String toString() {
        return "Task{" + "callable= " + task +", type= " + type +"}";
    }

    public <T> Callable<T> getCall() {
        return (Callable<T>) task;
    }
}
