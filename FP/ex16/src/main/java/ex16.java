import utils.ActiveObject;
import utils.PrimeUtils;
import utils.RSAKeyUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.concurrent.TimeUnit.SECONDS;
import static utils.PrimeUtils.generateProbablePrime;

/**
 * This example shows how to use modern Java lambda expressions and
 * the {@link Future} interface to create an {@link ActiveObject}
 * that's implemented via a Java virtual {@link Thread} closure.  A
 * closure is a persistent scope that holds on to local variables even
 * after the code execution has moved out of a block.  Instances of
 * {@link ActiveObject} are used to check the primality of large
 * numbers as part of computing RSA public and private keys.
 */
@SuppressWarnings("SameParameterValue")
public class ex16 {
    /**
     * The number of iterations to perform.
     */
    private static final int sNUM_ITERATIONS = 5;

    /**
     * The number of seconds to wait for each iteration.
     */
    private static final long sWAIT_TIME = 1;

    /**
     * Shorthand for an {@link ArrayList} of {@link ActiveObject}s
     * that are used to check the primality of large numbers.
     */
    static class ActiveObjectList
        extends ArrayList<ActiveObject<BigInteger,
                SimpleEntry<BigInteger, Boolean>>> {
    }

    /**
     * This method provides the main entry point into this test
     * program.
     */
    static public void main(String[] argv) throws Exception {
        System.out.println("Entering ActiveObjectPrimeTest");

        // Create two random large prime BigInteger objects.
        var prime0 = generateProbablePrime(1024);
        var prime1 = generateProbablePrime(1024);

        // Create a List of two ActiveObjects that concurrently (and
        // exhaustively) check the primality of large numbers in
        // background threads.
        ActiveObjectList primalityChecks = new ActiveObjectList() {{
            add(new ActiveObject<>(PrimeUtils::checkIfPrime,
                                   prime0));
            add(new ActiveObject<>(PrimeUtils::checkIfPrime,
                                   prime1));
        }};

        // This List holds the results of the primality checks.
        var results =
            // Try to perform deterministic primality checks,
            // which time out if the checks take too long.
            tryDeterministicChecks(primalityChecks,
                                   sWAIT_TIME);

        // If we didn't get both primality results then perform the
        // probabilistic primality checks.
        if (results.size() != 2)
            probabilisticChecks(prime0, prime1, results);

        // Print the results of the primality checks.
        checkAndPrintResults(results.get(0),
                             results.get(1));

        // Create a new RSA key pair based on the primality check.
        var keyPair = RSAKeyUtils
            .generateKeyPair(results.get(0).getKey(),
                             results.get(1).getKey());

        // Print the public and private keys.
        System.out.println("Public key = "
                           + keyPair.publicKey().toString()
                           + "\nPrivate key = "
                           + keyPair.privateKey().toString());

        System.out.println("Leaving ActiveObjectPrimeTest");
    }

    /**
     * Try to get the results of the {@code primalityChecks}, but only
     * wait for {@code waitTime} seconds.
     *
     * @param primalityChecks The {@link ActiveObjectList} of the
     *                        primality checks
     * @param waitTime The amount of time to wait for the results
     */
    private static List<SimpleEntry<BigInteger, Boolean>>
        tryDeterministicChecks
        (ActiveObjectList primalityChecks,
         long waitTime) {
        // Create a List to hold the primality check results.
        List<SimpleEntry<BigInteger, Boolean>> results =
            new ArrayList<>();

        // Try to get the results for the 'primalityChecks' without
        // blocking indefinitely.
        for (var future : primalityChecks) {
            // Loop for 'sNUM_ITERATIONS'.
            // Could also do results
            // .add(future.get(waitTime * sNUM_ITERATIONS, SECONDS))
            // instead of the inner for loop.
            for (int i = 0; i < sNUM_ITERATIONS; i++) {
                if (future.isDone()) {
                    // If the 'future' is done, get the result and
                    // break out of the inner loop.
                    results.add(future.resultNow());
                    break;
                } else {
                    try {
                        // If the 'future' is not done, wait for up to
                        // 'waitTime' seconds.
                        future.get(waitTime, SECONDS);
                    } catch (Exception ignored) {
                        // Ignore any exceptions that may occur here.
                    }
                }
            }
            // Cancel the future if it's not done or canceled already.
            if (!future.isDone() && !future.isCancelled())
                future.cancel(true);
        }

        // Return the results (if any).
        return results;
    }

    /**
     * Handle the {@link TimeoutException} by performing probabilistic
     * primality checks, which run quickly.
     *
     * @param prime0  The first prime number
     * @param prime1  The second prime number
     * @param results The results {@link List} to update
     */
    private static void probabilisticChecks
        (BigInteger prime0,
         BigInteger prime1,
         List<SimpleEntry<BigInteger, Boolean>> results)
        throws ExecutionException, InterruptedException {
        switch (results.size()) {
        case 0:
            // If 'results' is empty, add the first two primes to it.
            results.add(new ActiveObject<>
                        (PrimeUtils::checkIfProbablePrime,
                         prime0).get());
            // We fall through in this case.
        case 1:
            // If 'results' has one entry, add the second prime to it.
            results.add(new ActiveObject<>
                        (PrimeUtils::checkIfProbablePrime,
                         prime1).get());
            break;
        default:
            // Throw an IllegalStateException if there are more than
            // one element in the 'results' List.
            throw new IllegalStateException
                ("Too many results in results List");
        }
    }

    /**
     * This method checks to ensure the results are both primes and
     * then prints the results.
     *
     * @param primeResult1 The result of the first check
     * @param primeResult2 The result of the second check
     * @throw IllegalStateException If either result is not prime
     */
    private static void checkAndPrintResults
        (SimpleEntry<BigInteger, Boolean> primeResult1,
         SimpleEntry<BigInteger, Boolean> primeResult2) {
        if (!primeResult1.getValue() || !primeResult2.getValue())
            throw new IllegalStateException
                ("One or both results are not primes");
        else
            // Print the results.
            System.out.println("The number "
                               + primeResult1.getKey()
                               + "\nis prime and the number\n"
                               + primeResult2.getKey()
                               + "\nis prime.");
    }
}

