package algorithms;

import java.math.BigInteger;
import java.util.stream.LongStream;

/**
 * Classes that use Java objects with shared mutable state to
 * incorrectly or inefficiently compute factorials for {@link
 * BigInteger} objects.
 */
public class MutableState {
    /**
     * Shows how race conditions can occur when mutable state is
     * shared between threads in the Java common fork-join pool using
     * a Java parallel stream and the forEach() terminal operation.
     */
    public static class BuggyFactorial {
        /**
         * This class stores a running total of the factorial and
         * provides a (buggy) method to multiply this running total
         * with a value of 'n'.
         */
        static class Total {
            /**
             * The running total of the factorial (shared mutable
             * state).
             */
            BigInteger mTotal = BigInteger.ONE;

            /**
             * Multiply the running total by {@code n}.  
             */
            void mult(BigInteger n) {
                // Since this code is not synchronized it will incur
                // race conditions when run on a multi-core processor!
                mTotal = mTotal.multiply(n);
            }
        }

        /**
         * Attempts to return the factorial for {@code n}.  The result
         * is inaccurate, however, since there are race conditions due
         * to accessing and updating shared mutable state.
         *
         * @param n The {@link BigInteger} to compute 'n' factorial for
         * @return A {@link BigInteger} containing the nth factorial
         */
        public static BigInteger factorial(BigInteger n) {
            // This object is (incorrectly) shared by multiple threads
            // in the Java common fork-join pool.
            Total t = new Total();

            LongStream
                // Create a stream of longs from 1 to n.
                .rangeClosed(1, n.longValue())

                // Run stream operations concurrently in the common
                // fork-join pool.
                .parallel()

                // Convert each long value to a BigInteger.
                .mapToObj(BigInteger::valueOf)

                // Multiply each value in the stream by the running
                // total, but 't' is not properly synchronized.
                .forEach(t::mult);

            // Return the total, which may incur memory visibility
            // problems on a multi-core processor.
            return t.mTotal;
        }
    }

    /**
     * Shows how Java synchronized methods can (inefficiently) avoid
     * race conditions when state is shared between threads in the
     * Java common fork-join pool.
     */
    public static class SynchronizedParallelFactorial {
        /**
         * This class stores a running total of the factorial and is
         * properly (albeit inefficiently) synchronized to avoid race
         * conditions and memory visibility problems.
         */
        static class Total {
            /**
             * The running total of the factorial.
             */
            BigInteger mTotal = BigInteger.ONE;

            /**
             * Multiply the running total by {@code n}.  
             */
            synchronized void mult(BigInteger n) {
                // Although this synchronized method avoiding race
                // conditions it incurs excessive lock contention.
                mTotal = mTotal.multiply(n);
            }

            /**
             * @return The final value of the factorial computation
             */
            synchronized BigInteger get() {
                // This synchronized method ensures visibility of the
                // result in multi-core processor memory.
                return mTotal;
            }
        }

        /**
         * Inefficiently return the factorial for {@code n} using a
         * Java parallel stream and the forEach() terminal operation.
         *
         * @param n The {@link BigInteger} to compute 'n' factorial for
         * @return A {@link BigInteger} containing the nth factorial
         */
        public static BigInteger factorial(BigInteger n) {
            // This mutable object is correctly (but inefficiently)
            // shared by threads in the Java common fork-join pool.
            Total t = new Total();

            LongStream
                // Create a stream of longs from 1 to n.
                .rangeClosed(1, n.longValue())

                // Run stream operations concurrently in the common
                // fork-join pool.
                .parallel()

                // Convert each long value to a BigInteger.
                .mapToObj(BigInteger::valueOf)

                // Multiply each value in the stream by the running
                // total, but 't' is inefficiently synchronized.
                .forEach(t::mult);

            // Return the total, which is will be visible the calling
            // thread.
            return t.get();
        }
    }
}
