package utils;

/**
 * This class implements the Singleton pattern to handle command-line
 * option processing.
 */
public class Options {
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
     * Controls whether to use parallel streams (defaults
     * to false).
     */
    private boolean mParallel = false;

    /**
     * Method to return the one and only singleton uniqueInstance.
     */
    public static Options instance() {
        if (mUniqueInstance == null)
            mUniqueInstance = new Options();

        return mUniqueInstance;
    }

    /**
     * Returns whether to use parallel streams or not.
     */
    public boolean getParallel() {
        return mParallel;
    }

    /**
     * Returns whether debugging output is generated.
     */
    public boolean getDiagnosticsEnabled() {
        return mDiagnosticsEnabled;
    }

    /**
     * Sets whether debugging output is generated.
     */
    public boolean setDiagnosticsEnabled(boolean enable) {
        return mDiagnosticsEnabled = enable;
    }

    /**
     * Parse command-line arguments and set the appropriate values.
     */
    public void parseArgs(String[] argv) {
        if (argv != null) {
            for (int argc = 0; argc < argv.length; argc++)
                switch (argv[argc]) {
                    case "-d" -> mDiagnosticsEnabled = argv[++argc].equals("true");
                    case "-p" -> mParallel = true;
                    default -> {
                        printUsage(argv[argc]);
                        return;
                    }
                }
        }
    }

    /**
     * Conditionally prints the {@link String} depending on the current
     * setting of the Options singleton.
     */
    public static void print(String string) {
        if (Options.instance().getDiagnosticsEnabled())
            System.out.println(string);
    }

    /**
     * Print out usage and default values.
     */
    public void printUsage(String arg) {
        System.out.println("""
            %s is an invalid argument
            Usage:
            -d [true|false]
            -p""");
    }

    /**
     * Make the constructor private for a singleton.
     */
    private Options() {
    }
}
