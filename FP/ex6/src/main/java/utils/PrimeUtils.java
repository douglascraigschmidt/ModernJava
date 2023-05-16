package utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class contains utility methods for prime number generation and checking.
 */
public class PrimeUtils {
    /**
     * Define a Java record that holds the "plain old data" (POD) for
     * the result of a primality check.
     */
    public record PrimeResult(/*
                               * Value that was evaluated for primality.
                               */
                              BigInteger primeCandidate,

                              /*
                               * Result of the primality check.
                               */
                              Boolean isPrime) {}

    /**
     * Check if {@code primeCandidate} is prime or not.
     *
     * @param primeCandidate The number to check for primality
     * @return A {@link PrimeResult} record that contains the original
     *         {@code primeCandidate} and either true if it's prime or
     *         false if it's not prime.
     */
    public static PrimeResult checkIfPrime
        (BigInteger primeCandidate) {
        var isPrime = primeCandidate
            // Determine if the candidate is prime.
            .isProbablePrime(Integer.MAX_VALUE);

        // Return a record containing the prime candidate and the
        // result of checking if it's prime.
        return new PrimeResult(primeCandidate, isPrime);
    }

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
}

