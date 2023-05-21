import utils.MathUtils;
import utils.Options;
import utils.RandomUtils;
import utils.RunTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import static utils.ExceptionUtils.rethrowConsumer;

/**
 * This example demonstrates how to apply Java functional and
 * object-oriented programming features to create, start, and use
 * virtual and platform Thread objects in Java 19+, which contains an
 * implementation of lightweight user-mode threads (virtual threads).
 * You'll need to install JDK 19+ with gradle 7.6+ configured.
 */
public class ex1 {
    /**
     * Keeps track of the number of iterations.
     */
    private static final AtomicInteger sIterationCount =
        new AtomicInteger();

    /**
     * Main entry point into the test program.
     */
    public static void main(String[] argv) {
        // Initialize any command-line options.
        Options.instance().parseArgs(argv);

        // The number of virtual or platform Thread objects to create.
        var threadCount = Options.instance().numberOfElements();

        System.out.println("Entering test with "
            + threadCount
            + (Options.instance().virtualThreads()
            ? " virtual" : " platform")
            + " threads");

        // Generate a List of random integers.
        var randomIntegers = RandomUtils
            .generateRandomNumbers(threadCount,
                Integer.MAX_VALUE);

        // Create/start virtual Thread objects and use them to check
        // the primality of randomIntegers.
        RunTimer
            .timeRun(() -> startThreads(threadCount,
                    randomIntegers,
                    true),
                threadCount + " virtual threads");

        // Create/start platform Thread objects and use them to check
        // the primality of randomIntegers.
        RunTimer
            .timeRun(() -> startThreads(threadCount,
                    randomIntegers,
                    false),
                threadCount + " platform threads");

        // Print the timing results.
        System.out.println(RunTimer.getTimingResults());

        System.out.println("Leaving test");
    }

    /**
     * Demonstrate how to create and start many platform or virtual
     * Java {@link Thread} objects.
     *
     * @param randomIntegers The {@link List} of random integers to
     *                       check for primality
     * @param virtual        If true create a virtual {@link Thread}, else
     *                       create a platform {@link Thread}
     */
    private static void startThreads(int threadCount,
                                     List<Integer> randomIntegers,
                                     boolean virtual) {
        // Print a diagnostic periodically.
        IntConsumer printDiagnostic = i -> {
            if (Options.instance().printDiagnostic(i))
                Options.display(i
                    + (virtual ? " virtual" : " platform")
                    + " thread created");
        };

        // Create a List to hold Thread objects.
        List<Thread> threads = new ArrayList<>();

        // Iterate 'threadCount' times.
        for (int i = 0; i < threadCount; ++i) {
            // Use the functional printDiagnostic function.
            printDiagnostic.accept(i);

            // Create a Runnable containing a large random number to
            // check for primality.
            Runnable runnable =
                makeRunnable(randomIntegers.get(i));

            // Make a new Thread (either virtual or platform) for each
            // random Runnable.
            Thread thread = makeThread(runnable, virtual);

            // Add the Thread to the List.
            threads.add(thread);
        }

        // Start all the Thread objects.
        threads.forEach(Thread::start);

        // Join all the Thread objects (barrier synchronizer).
        threads.forEach(rethrowConsumer(Thread::join));
    }

    /**
     * This factory method creates and returns a new unstarted {@link
     * Thread} (either virtual or platform) that will run the given
     * {@link Runnable} after the {@link Thread} starts.
     *
     * @param runnable The {@link Runnable} to run in a new {@link
     *                 Thread}
     * @param virtual  If true the {@link Thread} should be a virtual
     *                 {@link Thread}, else it should be a platform {@link
     *                 Thread}
     * @return A new unstarted {@link Thread} (either virtual or
     * platform) that will run the given {@link Runnable}
     * after the {@link Thread} starts
     */
    public static Thread makeThread(Runnable runnable,
                                    boolean virtual) {
        var thread = virtual
            // Create a virtual thread, which is multiplexed atop
            // worker threads in a Java fork-join pool.
            ? Thread.ofVirtual()

            // Create a platform thread, i.e., 1-to-1 mapping with an
            // OS thread.
            : Thread.ofPlatform();

        return thread
            // Set the Runnable to run in the new Thread after it's
            // started.
            .unstarted(runnable);
    }

    /**
     * This factory method returns a new {@link Runnable} that checks
     * the given {@code integer} for primality.
     *
     * @param integer The number to check for primality
     * @return A {@link Runnable} that checks the primality of
     * {@code integer}
     */
    public static Runnable makeRunnable(int integer) {
        // Return a Runnable lambda that checks if 'integer' is prime.
        return () -> {
            // Determine if 'integer' is prime or not.
            var result = MathUtils.isPrime(integer);

            // Periodically print the result of checking for
            // primality.
            if (Options.instance()
                .printDiagnostic(sIterationCount.getAndIncrement())) {
                if (result == 0)
                    Options.display(integer + " is prime");
                else
                    Options.display(integer
                        + " is not prime with smallest factor "
                        + result);
            }
        };
    }
}
