import utils.RunTimer;

import java.math.BigInteger;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * This program implements various ways to compute factorials for
 * BigIntegers to demonstrate the performance of alternative parallel
 * and sequential algorithms, as well as the dangers of sharing
 * unsynchronized mutable state between threads.  It illustrates both
 * Java sequential and parallel streams.
 */
public class ex2 {
    /**
     * Max number of times to run the tests.
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

        // Initialize to the default value or the
        final BigInteger n = args.length > 0
            ? BigInteger.valueOf(Long.parseLong(args[0]))
            : BigInteger.valueOf(sDEFAULT_N);

        // Warm up the fork-join pool to ensure accurate timings.
        warmUpForkJoinThreads();

        // Run the various factorial tests.

        // Provides a baseline factorial implementation using a
        // sequential Java Stream.
        runTest("SequentialStreamFactorial",
                SequentialStreamFactorial::factorial,
                n);

        // Demonstrates how race conditions can occur when state is
        // shared between worker threads in the Java common fork-join
        // pool via a mutable shared object.
        runTest("BuggyFactorial1",
                BuggyFactorial1::factorial,
                n);

        // Shows how a synchronized statement can (inefficiently)
        // avoid race conditions when state is shared between Java
        // threads.
        runTest("SynchronizedParallelFactorial",
                SynchronizedParallelFactorial::factorial,
                n);

        // Shows how the modern Java reduce() operation avoids sharing
        // state between Java threads altogether.
        runTest("ParallelStreamFactorial",
                ParallelStreamFactorial::factorial,
                n);

        // Print the results in descending order from fastest to
        // slowest.
        System.out.println(RunTimer.getTimingResults());

        System.out.println("Ending Factorial Tests");
    }

    /**
     * Run the given {@code factorialTest} {@link Function} to compute
     * the factorial of {@code n} and print the result.
     */
    private static <T> void runTest(String factorialTest,
                                    Function<T, T> factorial,
                                    T n) {
        // Run the garbage collector to ensure each test starts with
        // all the memory.
        System.gc();

        RunTimer
            // Time the test.
            .timeRun(() -> {
                    IntStream
                        // Iterate sMAX_ITERATION times.
                        .range(0, sMAX_ITERATIONS)
                        
                        // Apply the factorial computation.
                        .forEach(i -> factorial.apply(n));
            },
            factorialTest);

        System.out.println(factorialTest
                           + " factorial for "
                           + n 
                           + " = \n"
                           + factorial.apply(n));
    }

    /**
     * Provides a baseline factorial implementation using a sequential
     * Java Stream.
     */
    private static class SequentialStreamFactorial {
        /**
         * Return the factorial for the given {@code n} using a
         * sequential stream and the reduce() terminal operation.
         */
        static BigInteger factorial(BigInteger n) {
            return LongStream
                // Create a stream of longs from 1 to n.
                .rangeClosed(1, n.longValue())

                // Create a BigInteger from the long value.
                .mapToObj(BigInteger::valueOf)

                // Performs a reduction on the elements of this stream
                // to compute the factorial.
                .reduce(BigInteger.ONE,
                        BigInteger::multiply);
        }
    }

    /**
     * Demonstrates how race conditions can occur when state is shared
     * between worker threads in the Java common fork-join pool via a
     * mutable shared object and the forEach() terminal operation.
     */
    private static class BuggyFactorial1 {
        /**
         * This class keeps a running total of the factorial and
         * provides a (buggy) method for multiplying this running
         * total with a value n.
         */
        static class Total {
            /**
             * The running total of the factorial.
             */
            BigInteger mTotal = BigInteger.ONE;

            /**
             * Multiply the running total by {@code n}.  This method
             * is not synchronized, so it will incur race conditions
             * when run on a multi-core processor!
             */
            void multiply(BigInteger n) {
                mTotal = mTotal.multiply(n);
            }
        }  

        /**
         * Attempts to return the factorial for the given {@code n}.
         * There are race conditions wrt accessing shared state,
         * however, so the result may not always be correct.
         */
        static BigInteger factorial(BigInteger n) {
            Total t = new Total();

            LongStream
                // Create a stream of longs from 1 to n.
                .rangeClosed(1, n.longValue())

                // Run the forEach() terminal operation concurrently.
                .parallel()

                // Create a BigInteger from the long value.
                .mapToObj(BigInteger::valueOf)

                // Multiply the latest value in the range by the
                // running total (not properly synchronized).
                .forEach(t::multiply);

            // Return the total, which is also not properly
            // synchronized.
            return t.mTotal;
        }
    }

    /**
     * Shows how a synchronized statement can (inefficiently) avoid
     * race conditions when state is shared between Java threads.
     */
    private static class SynchronizedParallelFactorial {
        /**
         * This class keeps a running total of the factorial and
         * provides a synchronized method for multiplying this running
         * total with a value n.
         */
        static class Total {
            /**
             * The running total of the factorial.
             */
            BigInteger mTotal = BigInteger.ONE;

            /**
             * Multiply the running total by {@code n}.  This method
             * is synchronized to avoid race conditions.
             */
            synchronized void multiply(BigInteger n) {
                mTotal = mTotal.multiply(n);
            }

            /**
             * Synchronize get() to ensure visibility of the data.
             */
            synchronized BigInteger get() {
                return mTotal;
            }
        }

        /**
         * Return the factorial for the given {@code n} using a
         * parallel stream and the forEach() terminal operation.
         */
        static BigInteger factorial(BigInteger n) {
            Total t = new Total();

            LongStream
                // Create a stream of longs from 1 to n.
                .rangeClosed(1, n.longValue())

                // Run the forEach() terminal operation concurrently.
                .parallel()

                // Create a BigInteger from the long value.
                .mapToObj(BigInteger::valueOf)

                // Multiply the latest value in the range by the
                // running total (properly synchronized).
                .forEach(t::multiply);

            // Return the total.
            return t.get();
        }
    }

    /**
     * Shows how the modern Java reduce() operation avoids sharing
     * state between Java threads altogether.
     */
    private static class ParallelStreamFactorial {
        /**
         * Return the factorial for the given {@code n} using a
         * parallel stream and the reduce() terminal operation.
         */
        static BigInteger factorial(BigInteger n) {
            return LongStream
                // Create a stream of longs from 1 to n.
                .rangeClosed(1, n.longValue())

                // Run the reduce() terminal operation concurrently.
                .parallel()

                // Create a BigInteger from the long value.
                .mapToObj(BigInteger::valueOf)

                // Use the two-parameter variant of reduce() to
                // perform a reduction on the elements of this stream
                // to compute the factorial.  Note that there's no
                // shared state at all!
                .reduce(BigInteger.ONE,
                        BigInteger::multiply);
        }
    }

    /**
     * Warm up the threads in the fork/join pool so that the timing
     * results will be more accurate.
     */
    private static void warmUpForkJoinThreads() {
        System.out.println("Warming up the fork/join pool\n");

        for (int i = 0; i < sMAX_ITERATIONS; i++)
            ParallelStreamFactorial.factorial(BigInteger.valueOf(sDEFAULT_N));
    }
}

