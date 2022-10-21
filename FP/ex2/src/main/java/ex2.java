import algorithms.ImmutableState;
import algorithms.MutableState;
import utils.RunTimer;

import java.math.BigInteger;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * This program implements various ways of computing factorials for
 * BigIntegers to demonstrate the performance of alternative parallel
 * and sequential algorithms that use immutable or mutable Java
 * objects.  It also shows (1) the dangers of sharing unsynchronized
 * mutable state between threads and (2) the overhead of excessive
 * synchronization of shared mutable state.  Both Java sequential and
 * parallel streams are used in this program.
 */
public class ex2 {
    /**
     * The total number of iterations to run the timing tests.
     */
    private static final int sMAX_ITERATIONS = 20_000;

    /**
     * Default factorial number.
     */
    private static final int sDEFAULT_N = 1_000;

    /**
     * This is the entry point into the test program.
     */
    public static void main(String[] args) {
        System.out.println("Starting Factorial Tests");

        // Initialize to the default value or the value
        // of argv[0].
        final BigInteger n = args.length > 0
            ? BigInteger.valueOf(Long.parseLong(args[0]))
            : BigInteger.valueOf(sDEFAULT_N);

        // Warm up the fork-join pool to ensure accurate timings.
        warmUpForkJoinThreads();

        // Run the various factorial tests.

        // Provides a baseline factorial implementation using a
        // sequential Java Stream.
        runTest("SequentialStreamFactorial",
                ImmutableState.SequentialStreamFactorial::factorial,
                n);

        // Shows how race conditions can occur when mutable state is
        // shared between threads in the Java common fork-join pool
        // using a Java parallel stream and the forEach() terminal
        // operation.
        runTest("BuggyFactorial",
                MutableState.BuggyFactorial::factorial,
                n);

        // Shows how Java synchronized methods can (inefficiently)
        // avoid race conditions when state is shared between threads
        // in the Java common fork-join pool.
        runTest("SynchronizedParallelFactorial",
                MutableState.SynchronizedParallelFactorial::factorial,
                n);

        // Shows how the reduce() operation in the Java parallel
        // streams framework avoids sharing mutable state between
        // threads in the Java common fork-join pool.
        runTest("ParallelStreamFactorial",
                ImmutableState.ParallelStreamFactorial::factorial,
                n);

        // Print the timing results in descending order from fastest
        // to slowest.
        System.out.println(RunTimer.getTimingResults());

        System.out.println("Ending Factorial Tests");
    }

    /**
     * Run the given {@code factorialTest} {@link Function} to compute
     * the factorial of {@code n} and print the result.
     * 
     * @param algorithmName The name of the factorial algorithm to test
     * @param factorialAlgorithm The given factorial algorithm
     * @param n The value to compute'n' factorial for
     */
    private static <T> void runTest(String algorithmName,
                                    Function<T, T> factorialAlgorithm,
                                    T n) {
        // Run the garbage collector to ensure each test starts out
        // with all the available memory.
        System.gc();

        RunTimer
            // Time the test.
            .timeRun(() -> {
                    IntStream
                        // Iterate sMAX_ITERATION times.
                        .range(0, sMAX_ITERATIONS)

                        // Apply the factorial algorithm.
                        .forEach(i -> factorialAlgorithm.apply(n));
                },
                // Use algorithmName to record the elapsed time.
                algorithmName);

        // Apply the factorial algorithm and print the result.
        System.out.println(algorithmName
                           + " factorial for "
                           + n
                           + " = \n"
                           + factorialAlgorithm.apply(n));
    }

    /**
     * Warm up the threads in the fork/join pool so that the timing
     * results will be more accurate.
     */
    private static void warmUpForkJoinThreads() {
        System.out.println("Warming up the fork/join pool\n");

        for (int i = 0; i < sMAX_ITERATIONS; i++)
            ImmutableState
                .ParallelStreamFactorial
                .factorial(BigInteger.valueOf(sDEFAULT_N));
    }
}

