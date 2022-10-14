import java.util.Random;

/**
 * This example uses the Java streams framework to compose a pipeline
 * of functions that generate and check sMAX_COUNT positive odd random
 * numbers and print which are prime and which are not.  It also shows
 * how to use the Java {@code record} type.
 */
@SuppressWarnings("SameParameterValue")
public class ex1 {
    /**
     * The number of positive odd random numbers to check for
     * primality.
     */
    private static final int sMAX_COUNT = 20;

    /**
     * The main entry point into this program.
     */
    static public void main(String[] argv) {
        // Generate and check sMAX_COUNT odd random numbers and print
        // which are prime and which are not.
        checkForPrimes(sMAX_COUNT);
    }

    /**
     * Define a Java record that holds the "plain old data" (POD) for
     * the result of a primality check.
     */
    record PrimeResult(/*
                        * Value that was evaluated for primality.
                        */
                       int primeCandidate,

                       /*
                        * Result of the isPrime() method.
                        */
                       int smallestFactor) {}

    /**
     * This method generates and checks {@code count} positive odd
     * random numbers and prints which are prime and which are not.
     *
     * @param count The number of positive odd random numbers to
     *              check for primality
     */
    static void checkForPrimes(int count) {
        new Random()
            // Generate an infinite stream of random positive ints.
            .ints(1, Integer.MAX_VALUE)

            // Only allow odd numbers.
            .filter(ex1::isOdd)

            // Check each odd number to see if it's prime.
            .mapToObj(ex1::checkIfPrime)

            // Limit the stream to count items.
            .limit(count)

            // Print the results.
            .forEach(ex1::printResult);
    }

    /**
     * This method returns true if the {@code integer} param is an odd
     * number, else false.
     *
     * @param integer The parameter to check for oddness
     * @return true if the {@code integer} param is an odd number,
     *         else false
     */
    private static boolean isOdd(int integer) {
        // Use the bit-wise and operator to determine if 'integer' is
        // odd or not.
        return (integer & 1) == 1;
    }

    /**
     * Check if {@code primeCandidate} is prime or not.
     *
     * @param primeCandidate The number to check for primality
     * @return A {@link PrimeResult} record that contains the original
     * {@code primeCandidate} and either 0 if it's prime or its
     * smallest factor if it's not prime.
     */
    private static PrimeResult checkIfPrime(int primeCandidate) {
        // Return a record containing the prime candidate and the
        // result of checking if it's prime.
        return new PrimeResult(primeCandidate,
                               isPrime(primeCandidate));
    }

    /**
     * This method determines whether {@code primeCandidate} is prime.
     *
     * @param primeCandidate The number to check for primality
     * @return 0 if prime or the smallest factor if not prime
     */
    private static Integer isPrime(int primeCandidate) {
        int n = primeCandidate;

        // Check if n is a multiple of 2.
        if (n % 2 == 0)
            // Return smallest factor for non-prime number.
            return 2;

        // If not, then just check the odds for primality.
        for (int factor = 3;
             factor * factor <= n;
             // Skip over even numbers.
             factor += 2)
            if (n % factor == 0)
                // primeCandidate was not prime.
                return factor;

        // primeCandidate was prime.
        return 0;
    }

    /**
     * Print the {@code result}
     *
     * @param result The result of checking if a number is prime
     */
    private static void printResult(PrimeResult result) {
        // Check if number was not prime.
        if (result.smallestFactor() != 0)
            System.out.println(result.primeCandidate()
                               + " is not prime with smallest factor "
                               + result.smallestFactor());
        // The number is prime.
        else
            System.out.println(result.primeCandidate()
                               + " is prime");
    }
}

