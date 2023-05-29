package utils;

import java.math.BigInteger;
import java.security.SecureRandom;

import static java.util.AbstractMap.SimpleEntry;

/**
 * This class contains utility methods for prime number generation and
 * checking.
 */
public class PrimeUtils {
    /**
     * Generate a probable prime number of a specified bit length.
     *
     * @param bitLength The bit length of the prime number
     * @return A probable prime number
     */
    public static BigInteger generateProbablePrime(int bitLength) {
        return BigInteger.probablePrime(bitLength,
                                        new SecureRandom());
    }

    /**
     * Check if {@code primeCandidate} is probably prime or not.
     *
     * @param primeCandidate The number to check for primality
     * @return A {@link SimpleEntry} record that contains the original
     *         {@code primeCandidate} and either true if it's probably
     *         prime or false if it's not prime.
     */
    public static SimpleEntry<BigInteger, Boolean> checkIfProbablePrime
        (BigInteger primeCandidate) {
        var isPrime = primeCandidate
            // Determine if the candidate is prime.
            .isProbablePrime(Integer.MAX_VALUE);

        // Return a record containing the prime candidate and the
        // result of checking if it's prime.
        return new SimpleEntry<>(primeCandidate, isPrime);
    }

    /**
     * Check if {@code primeCandidate} is prime or not.
     *
     * @param primeCandidate The number to check for primality
     * @return A {@link SimpleEntry} record that contains the original
     *         {@code primeCandidate} and either true if it's prime or
     *         false if it's not prime.
     */
    public static SimpleEntry<BigInteger, Boolean> checkIfPrime
        (BigInteger primeCandidate) {
        var isPrime = PrimeUtils
            // Determine if the candidate is prime.
            .isPrime(primeCandidate).equals(BigInteger.ZERO);

        // Return a record containing the prime candidate and the
        // result of checking if it's prime.
        return new SimpleEntry<>(primeCandidate, isPrime);
    }

    /**
     * This method determines whether {@code primeCandidate} is prime.
     *
     * @param primeCandidate The number to check for primality
     * @return 0 if prime or the smallest factor if not prime
     */
    public static BigInteger isPrime(BigInteger primeCandidate) {
        // Perform a sanity check.
        if (primeCandidate.compareTo(BigInteger.ONE) <= 0)
            throw new IllegalArgumentException
                ("Number must be greater than 1");

        // If 'primeCandidate' is divisible by 2, return it as the
        // smallest factor.
        if (primeCandidate
            .mod(BigInteger.TWO)
            .equals(BigInteger.ZERO))
            return BigInteger.TWO;

        for (BigInteger i = BigInteger
                 // Initialize 'i' to 3.
                 .valueOf(3);
             // Continue loop while 'i * i' <= primeCandidate.
             i.multiply(i).compareTo(primeCandidate) <= 0;
             // Increment 'i' by 2 in each iteration to skip over even
             // numbers.
             i = i.add(BigInteger.TWO)) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("["
                                   + Thread.currentThread().threadId()
                                   + "] "
                                   +
                                   "Interrupted, so return -1 as smallest factor.");
                // Return -1 if interrupted.
                return BigInteger.valueOf(-1);
            }
            // If 'primeCandidate' is divisible by 'i', then return
            // 'i' as the smallest factor.
            if (primeCandidate.remainder(i).equals(BigInteger.ZERO))
                return i;
        }

        // If no factors found, the number is prime.
        return BigInteger.ZERO;
    }
}

