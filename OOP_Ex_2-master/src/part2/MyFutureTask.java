package part2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * The following class is used as an adapter class.
 * The FutureTask class objects can not be compared, the problem is that the objects that are passed to the priority queue (submit method)
 * must be comparable objects.
 * The MyFutureTask class allows us to submit FutureTask objects that are comparable to the priority queue
 */
class MyFutureTask<V> extends FutureTask<V> implements Comparable<MyFutureTask> {

    private int priority = 0;

    //constructor
    public MyFutureTask(Callable<V> callable, int priority) {
        super(callable);
        this.priority = priority;
    }

    /**
     * @return the objects priority
     */
    public int getPriority(){
        return this.priority;
    }


    /**
     * compares between two MyFutureTask objects
     * @param other MyFutureTask object
     * @return 0, if the priorities are equal
     *         1, if this object has a greater priority
     *        -1, if the given object has a greater priority
     */
    @Override
    public int compareTo(MyFutureTask other) {
        int diff = this.getPriority() - other.getPriority();
        return Integer.compare(0, diff);
    }
}
