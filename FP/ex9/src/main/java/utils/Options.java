package utils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * This class implements the Singleton pattern to handle command-line
 * option processing.
 */
public class Options {
    /**
     * Logging tag.
     */
    private static final String TAG = Options.class.getName();

    /** 
     * The singleton {@link Options} instance.
     */
    private static Options mUniqueInstance = null;

    /**
     * Controls whether debugging output will be generated (defaults
     * to false).
     */
    private boolean mDiagnosticsEnabled = false;

    /**
     * Controls how many longs are generated.
     */
    private int mCount = 500;

    /**
     * Controls the max value of the random numbers.
     */
    private int mMaxValue = Integer.MAX_VALUE;

    /**
     * Keeps track of whether to run the tests using a sequential or
     * parallel stream.
     */
    private boolean mParallel = true;

    /**
     * Count the number of calls to isPrime() as a means to determine
     * the benefits of caching.
     */
    private final AtomicInteger mPrimeCheckCounter =
        new AtomicInteger(0);

    /**
     * Method to return the one and only singleton uniqueInstance.
     */
    public static Options instance() {
        if (mUniqueInstance == null)
            mUniqueInstance = new Options();

        return mUniqueInstance;
    }

    /**
     * Returns whether debugging output is generated.
     */
    public boolean diagnosticsEnabled() {
        return mDiagnosticsEnabled;
    }

    /**
     * Returns whether to run the stream in parallel or not.
     */
    public boolean parallel() {
        return mParallel;
    }

    /**
     * Returns the number of integers to generate.
     */
    public int count() {
        return mCount;
    }

    /**
     * Returns the max value
     */
    public int maxValue() {
        return mMaxValue;
    }

    /**
     * @return The mPrimeCheckCounter
     */
    public AtomicInteger primeCheckCounter() {
        return mPrimeCheckCounter;
    }

    /**
     * Print the string with thread information included.
     */
    public static void print(String string) {
        System.out.println("[" +
                           Thread.currentThread().threadId()
                           + "] "
                           + string);
    }

    /**
     * Print the debug string with thread information included if
     * diagnostics are enabled.
     */
    public static void debug(String string) {
        if (mUniqueInstance.mDiagnosticsEnabled)
            System.out.println("[" +
                    Thread.currentThread().threadId()
                    + "] "
                    + string);
    }

    /**
     * Parse command-line arguments and set the appropriate values.
     */
    public void parseArgs(String[] argv) {
        if (argv != null) {
            for (int argc = 0; argc < argv.length; argc += 2)
                switch (argv[argc]) {
                    case "-d" -> mDiagnosticsEnabled = argv[argc + 1].equals("true");
                    case "-c" -> mCount = Integer.parseInt(argv[argc + 1]);
                    case "-m" -> mMaxValue = Integer.parseInt(argv[argc + 1]);
                    case "-p" -> mParallel = argv[argc + 1].equals("true");
                    default -> {
                        printUsage();
                        return;
                    }
                }
            if (mMaxValue - mCount <= 0)
                throw new IllegalArgumentException("maxValue - count must be greater than 0");
        }
    }

    /**
     * Print out usage and default values.
     */
    private void printUsage() {
        System.out.println("Usage: ");
        System.out.println("-c [n] "
                           + "-d [true|false] "
                           + "-m [maxValue] "
                           + "-p [true|false]");
    }

    /**
     * Make the constructor private for a singleton.
     */
    private Options() {
    }
}
