package algorithms;

import java.math.BigInteger;
import java.util.stream.LongStream;

/**
 * Classes that use Java objects with immutable state to compute
 * factorials for {@link BigInteger} objects.
 */
public class ImmutableState {
    /**
     * Provides a baseline factorial implementation using a sequential
     * Java Stream.
     */
    public static class SequentialStreamFactorial {
        /**
         * Return the factorial for {@code n} using a Java sequential
         * stream and the {@code reduce()} terminal operation.
         *
         * @param n The {@link BigInteger} to compute 'n' factorial
         *           for
         * @return A {@link BigInteger} containing the 'nth' factorial
         */
        public static BigInteger factorial(BigInteger n) {
            return LongStream
                // Create a stream of longs from 1 to n.
                .rangeClosed(1, n.longValue())

                // Convert each long value to a BigInteger.
                .mapToObj(BigInteger::valueOf)

                // Compute the factorial by performing pair-wise
                // reductions on elements in this stream.
                .reduce(BigInteger.ONE, // Initial value.
                        BigInteger::multiply); // Accumulator.
        }
    }

    /**
     * Shows how the {@code reduce()} operation in the Java parallel
     * streams framework avoids sharing mutable state between threads
     * in the Java common fork-join pool.
     */
    public static class ParallelStreamFactorial {
        /**
         * Return the factorial for {@code n} using a Java parallel
         * stream and the {@code reduce()} terminal operation.
         *
         * @param n The {@link BigInteger} to compute 'n' factorial
         *          for
         * @return A {@link BigInteger} containing the nth factorial
         */
        public static BigInteger factorial(BigInteger n) {
            return LongStream
                // Create a stream of longs from 1 to n.
                .rangeClosed(1, n.longValue())

                // Run stream operations concurrently in the common
                // fork-join pool.
                .parallel()

                // Convert each long value to a BigInteger.
                .mapToObj(BigInteger::valueOf)

                // Use the three-parameter variant of reduce() to
                // perform pair-wise reductions on elements in this
                // stream to compute the factorial, which avoids using
                // shared mutable state!
                .reduce(BigInteger.ONE, // Initial value.
                        BigInteger::multiply, // Accumulator.
                        BigInteger::multiply); // Combiner.
        }
    }
}

