import utils.PrimeUtils;

import java.math.BigInteger;

import static utils.PrimeUtils.PrimeResult;

/**
 * This class demonstrates how to implement a closure using modern
 * Java features that concurrently checks the primality of a
 * {@link BigInteger}.
 */
@SuppressWarnings("SameParameterValue")
public class CheckPrimality {
    /**
     * The closure below updates this private field to store
     * the results of the primality check.
     */
    private PrimeResult mPrimeResult;

    /**
     * Store the {@link Thread} object used to perform the
     * primality check in the background.
     */
    private final Thread mThread;

    /**
     * This factory method creates a closure that will run in a
     * background {@link Thread} and compute the primality of a
     * {@link BigInteger}.
     *
     * @return A {@link Thread} that runs in the background
     *         computing the primality of a {@link BigInteger}
     */
    private Thread makeThreadClosure(BigInteger n) {
        // Create and return a new Thread whose Runnable lambda
        // expression defines a closure that checks the param 'n'
        // for primality.
        return new Thread(() -> {
                // The private field is updated within the
                // closure.
                mPrimeResult = PrimeUtils
                    // Check 'n' for primality.
                    .checkIfPrime(n);
        });
    }

    /**
     * The constructor creates/starts/runs a {@link Thread}
     * closure to check the primality of {@code n}.
     *
     * @param n The number to check for primality
     */
    CheckPrimality(BigInteger n) {
        // Create a Thread closure.
        mThread = makeThreadClosure(n);

        // Start the Thread.
        mThread.start();
    }

    /**
     * @return The smallest factor of the number
     * @throws InterruptedException If the Thread is interrupted
     */
    PrimeResult getResult() throws InterruptedException {
        // Wait until the primality checking is done.
        mThread.join();

        // Return the result.
        return mPrimeResult;
    }
}

