import algorithms.ImmutableState;
import algorithms.MutableState;
import utils.RunTimer;

import java.math.BigInteger;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * This program implements four ways of computing factorials for
 * BigIntegers to demonstrate the correctness and performance of
 * different parallel and sequential algorithms that use immutable or
 * mutable Java objects.  It also shows (1) the dangers of sharing
 * unsynchronized mutable state between threads and (2) the overhead
 * of excessive synchronization of shared mutable state.  Java
 * sequential and parallel streams are used in this program to
 * compose both "pure" functions and functions with side effects.
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

        // Initialize the factorial value to compute to either
        // sDEFAULT_N or the value of argv[0] provided on
        // the command-line by the user.
        final BigInteger n = args.length > 0
            ? BigInteger.valueOf(Long.parseLong(args[0]))
            : BigInteger.valueOf(sDEFAULT_N);

        // Warm up the threads in the Java common fork-join pool so
        // that the timing results will be more accurate.
        warmUpForkJoinThreads();

        // Run the four factorial tests.

        // Provides a baseline factorial implementation using a
        // Java sequential Stream that doesn't leverage multiple
        // cores.
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
        // streams framework eliminates shared mutable state between
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
     * the factorial of {@code n}, record the time needed to compute
     * this value, and then print the result.
     * 
     * @param algorithmName The name of the factorial algorithm to test
     * @param factorialAlgorithm The given factorial algorithm
     * @param n The value to compute 'n' factorial for
     */
    private static <T> void runTest(String algorithmName,
                                    Function<T, T> factorialAlgorithm,
                                    T n) {
        // Run the garbage collector to ensure each test starts out
        // with access to all available memory.
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
     * Warm up the threads in the Java common fork-join pool so that
     * the timing results will be more accurate.
     */
    private static void warmUpForkJoinThreads() {
        System.out.println("Warming up the fork/join pool\n");

        for (int i = 0; i < sMAX_ITERATIONS; i++)
            ImmutableState
                .ParallelStreamFactorial
                .factorial(BigInteger.valueOf(sDEFAULT_N));
    }
}

