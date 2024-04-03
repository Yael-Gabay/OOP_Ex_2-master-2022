# OOP_Ex_2

## Part 1

In the following assigment we were told to create a random number of text files and return their total number of lines.

We have implementad it in three different ways:
* Without Threads
* Using Threads
* Using ThreadPool

<b>The class main methods:</b>
* getNumOfLines- Returns the number of lines in all files

The method loops all lines in a for loop, and if there is a next line (using Scanner library) add 1 to the counter (=numOfLines), return counter.

* getNumOfLinesThreads- Returns the number of lines in all files, by using Threads 

In this method we use multithreading. For each file we allocate a thread, which means that the number of lines is calculated for each file in a seperate thread.
Then, loop all files, if the thread has finished add the result to the total. Return total.

* getNumOfLinesThreadPool- Returns the number of lines in all files, by using ExecutorService

The method creates an array of fileThread object, the size of the array is as the size of the number of files. For every file a thread is allocated.
all the threads a 


## RESULTS
Note: n represents the number of files

<img src=https://github.com/ChenLipschitz/OOP_Ex_2/blob/master/Images/100Files.png alt="100files"> <img src=https://github.com/ChenLipschitz/OOP_Ex_2/blob/master/Images/1000Files.png alt="1000files"> <img src=https://github.com/ChenLipschitz/OOP_Ex_2/blob/master/Images/10000Files.png alt="10000files">

According to the results above, all the methods calculated the same number of lines in total. The main difference between the methods is the running time.
When we create a large number of files the threadPool method is usually, more efficient since thread pool reuses previously created threads to execute current tasks.  It offers a solution to the problem of thread cycle overhead and resource thrashing. Since the thread is already existing when the request arrives, the delay introduced by thread creation is eliminated, making the application more responsive (for more info click <a href=https://www.geeksforgeeks.org/thread-pools-java/> here </a>).
After threadPool, multi threading will be the fastest.

Multi threading allows concurrent execution of two or more parts of a program for maximum utilization of CPU. Each part of such program is called a thread. So, threads are light-weight processes within a process. (for more info click <a href=https://www.geeksforgeeks.org/multithreading-in-java/> here </a>).

Why threadPool is better and faster in our case?

In threadPool we create a certain amount of threads. When a thread is done we reuse it for another task. For example, we can use only 4 threads in order to manage 100 tasks. As opposed to multi threading.

In multi threading for each task a new thread is created. For example, for 100 tasks we must create 100 threads. (Note that the creation of a thread is an expensive process).

Therefore thread pool is much cheaper and takes less runnig time than multi threading.

In the case of a small number of files, i.e 1, the ultimate way to calculate the number of lines will be without threads because it's much cheaper and the running time is legit.


## Part 2
The goal- extend the functionality of Javas Concurrency Framework of priority in threads.
Meaning- At the momment it's impossible to prioritize tasks in ThreadPool. Therefore we implemented a new type- ThreadPool with the ability to execute tasks acorrding to a given priority.

<b>The classes:</b>

* TaskType
* Task
* MyFutureTask
* CustomExecutor

## TaskType

An enum class. Represents the tasks priority according to its type.

## Task

Represents the task object. The class extends the FutureTask class and implements the Callable and Comparable classes.
The Callable class allows Task to run asynchronously with a return value, where a the Comparable class allows to compare betwwen to Task objects.
The compareTo is for determining which task will be executed first in the priority queue.

## MyFutureTask

Represents a FutureTask object with the ability to compare between two objects.
This class is an adapter class between FutureTask to CustomExecutor class.
The PriorityBlockingQueue gets only Callable objects (Note that callable does not has the comparTo method). Inorder to execute the tasks according to their priority a comparable method is must be added. Therefoe, we created an adapter class. MyFutureTask has the same functionalities as FutureTask but with an upgrade- the CompareTo method.

## CustomExecutor

The CustomExecutor class is an implementation of a ThreadPool with the ability to execute tasks according to their priority by using a BlockingPriorityQueue.

## Design Patterns

In both parts we implemented the S.O.L.I.D principles.

In the second part we also took into a considuration the Adapter design Pattern and the Factory method design Pattern.

## Authors

@YaelGabay

@ChenLipschitz

## Bibliography
* <a href=https://www.geeksforgeeks.org/thread-pools-java/> geeksforgeeks </a>
* <a href=https://coderstea.in/post/java/using-thread-pool-in-java-to-recycle-the-threads/> CodersTea </a>
