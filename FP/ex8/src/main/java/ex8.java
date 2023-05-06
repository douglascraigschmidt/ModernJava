import utils.Options;
import utils.PrimeUtils;
import utils.RunTimer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static utils.RandomUtils.generateRandomData;

/**
 * This example benchmarks the use of Java object-oriented and
 * functional programming features in conjunction with Java {@link
 * ConcurrentHashMap} to compute/cache/retrieve large prime numbers.
 * This example also demonstrates the Java {@code record} data type.
 */
public class ex8 {
    /**
     * Count the number of pending items.
     */
    private final AtomicInteger mPendingItemCount =
        new AtomicInteger(0);

    /**
     * A list of randomly-generated large integers.
     */
    private final List<Integer> mRandomIntegers;

    /**
     * Main entry point into the test program.
     */
    static public void main(String[] argv) {
        // Create and run the tests.
        new ex8(argv).run();
    }

    /**
     * Constructor initializes the fields.
     */
    ex8(String[] argv) {
        // Parse the command-line arguments.
        Options.instance().parseArgs(argv);

        // Generate random data for use by the various hashmaps.
        mRandomIntegers =
            generateRandomData(Options.instance().count(),
                               Options.instance().maxValue());
    }

    /**
     * Run all the tests and print the results.
     */
    private void run() {
        RunTimer
            // Time how long this test takes to run.
            .timeRun(() ->
                     // Run the test using the given memoizer.
                     runTest(new ConcurrentHashMap<>(),
                             true,
                             "Concurrent Test"),
                     "Concurrent Test");

        RunTimer
            // Time how long this test takes to run.
            .timeRun(() ->
                     // Run the test using the given memoizer.
                     runTest(new ConcurrentHashMap<>(),
                             false,
                             "Sequential Test"),
                     "Sequential Test");

        // Print the results.
        System.out.println(RunTimer.getTimingResults());
    }

    /**
     * Run the prime number test.
     * 
     * @param primeCache A memoizing cache that maps candidate primes
     *                   to their smallest factor (if they aren't
     *                   prime) or 0 if they are prime
     * @param parallel Run in parallel if true, else run sequentially
     * @param testName Name of the test
     */
    private void runTest
        (Map<Integer, Integer> primeCache,
         boolean parallel,
         String testName) {
        Options.print("Starting "
                      + testName
                      + " with count = "
                      + Options.instance().count());

        // Reset the counter.
        Options.instance().primeCheckCounter().set(0);

        this
            // Generate a stream of random large numbers.
            .publishRandomIntegers(parallel)

            // Print stats if we're debugging.
            .peek(item -> Options
                  .debug("processed item: "
                         + item
                         + ", publisher pending items: "
                         + mPendingItemCount.incrementAndGet()))

            // Check each random number to see if it's prime.
            .map(number -> PrimeUtils
                 .checkIfPrime(number, primeCache))
            
            // Handle the results.
            .forEach(this::handleResult);

        Options.print("Leaving "
                      + testName
                      + " with "
                      + Options.instance().primeCheckCounter().get()
                      + " prime checks ("
                      + (Options.instance().count()
                         - Options.instance().primeCheckCounter().get())
                      + ") duplicates");
    }

    /**
     * Publish a stream of random large {@link Integer} objects.
     *
     * @param parallel True if the stream should be parallel, else
     *                 false
     * @return Return a stream containing random large numbers
     */
    private Stream<Integer> publishRandomIntegers(boolean parallel) {
        Stream<Integer> intStream = mRandomIntegers
            // Convert the list into a stream.
            .stream();

        // Conditionally convert the stream to a parallel stream.
        if (parallel)
            intStream.parallel();

        // Return the stream.
        return intStream;
    }

    /**
     * Handle the result by printing it if debugging is enabled.
     *
     * @param result The result of checking if a number is prime
     */
    private void handleResult(PrimeUtils.PrimeResult result) {
        // Print the results.
        if (result.smallestFactor() != 0) {
            Options.debug(result.primeCandidate()
                          + " is not prime with smallest factor "
                          + result.smallestFactor());
        } else {
            Options.print(result.primeCandidate()
                          + " is prime");
        }

        Options.debug("consumer pending items: "
                      + mPendingItemCount.decrementAndGet());
    }
}
    
