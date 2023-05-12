package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ThreadUtils {
    /**
     * This factory method creates a {@link List} of Java {@link
     * Thread} objects that can be joined when their processing is
     * done.
     *
     * @param inputList A {@link List} of input data
     * @param task {@link Function} to run in each {@link Thread}
     * @return {@link List} of {@link Thread} objects that run the
     *         {@code task}
     */
    public static <T, R> List<Thread> makeThreads
        (List<T> inputList,
         Function<T, R> task) {
        // Create a new List.
        List<Thread> workerThreads = new ArrayList<>();

        // Create a Thread for each input string to perform processing
        // designated by the task parameter.
        inputList.forEach
            (input -> workerThreads
             // Add a new Thread to the List.
             .add(new Thread(() ->
                             // Create a lambda to run the task in
                             // a new Thread.
                             task.apply(input))));

        // Return the List of workerThreads.
        return workerThreads;
    }
}
