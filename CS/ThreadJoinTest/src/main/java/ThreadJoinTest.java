import utils.TestDataFactory;
import utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.ExceptionUtils.rethrowConsumer;

/**
 * This program implements an "embarrassingly parallel" app that
 * searches for phrases in a {@link List} of {@link String} objects,
 * each containing a different work of William Shakespeare.  It
 * demonstrates the use of modern Java functional programming features
 * (such as lambda expressions, method references, and functional
 * interfaces) in conjunction with Java {@link Thread} methods (e.g.,
 * {@code Thread.start()} to run threads and {@code Thread.join()} to
 * wait for all running threads).
 * 
 * This test starts a {@link Thread} for each input {@link String} and
 * uses {@code Thread.join()} to wait for all threads to finish.  This
 * implementation requires no Java synchronization mechanisms other
 * than what's provided by the Java {@link Thread} class.
 */
public class ThreadJoinTest
       implements Runnable {
    /**
     * A file containing the complete works of William Shakespeare.
     */
    private static final String sSHAKESPEARE_DATA_FILE =
        "completeWorksOfShakespeare.txt";

    /**
     * A {@link List} of phrases to search for in the complete works
     * of Shakespeare.
     */
    private static final String sPHRASE_LIST_FILE =
        "phraseList.txt";

    /**
     * The {@link List} of {@link String} objects, each of which
     * contains a work of Shakespeare.
     */
    private static final List<String> mInputList = TestDataFactory
        .getInput(sSHAKESPEARE_DATA_FILE, "@");

    /**
     * The {@link List} of phrases to search for in the works.
     */
    private static final List<String> mPhrasesToFind = TestDataFactory
        .getPhraseList(sPHRASE_LIST_FILE);

    /**
     * This is the main entry point into the program.
     */
    static public void main(String[] args) {
        System.out.println("Starting ThreadJoinTest");

        // Create/run an object to search for phrases in parallel.
        new ThreadJoinTest().run();

        System.out.println("Ending ThreadJoinTest");
    }

    /**
     * Start the threads to perform the parallel phrase searches.
     */
    @Override
    public void run() {
        // Call the makeWorkerThreads() factory method to create a
        // List of Thread objects that each run the processInput()
        // method reference.  These Thread objects will be joined
        // after they process the input String objects.
        var workerThreads = ThreadUtils
            .makeWorkerThreads(mInputList, this::processInput);

        // Iterate through the List of Thread objects and start a
        // Thread for each input String.
        workerThreads.forEach(Thread::start);

        // This concise solution iterates through the Thread objects
        // and pass the Thread.join() method reference as a barrier
        // synchronizer to wait for all threads to finish.  The
        // rethrowConsumer() adapter converts a checked exception to
        // an unchecked exception.
        workerThreads
            .forEach(rethrowConsumer(Thread::join));

        /*
        // This less concise solution iterates through the List of
        // Thread objects and join with them all, which is a form of
        // barrier synchronization.
        workerThreads.forEach(thread -> {
        try {
        thread.join();
        } catch (Exception e) {
        throw new RuntimeException(e);
        }});
        */
    }

    /**
     * This method runs in a background {@link Thread} and searches
     * the {@code input} for all occurrences of the phrases to find.
     *
     * @param input Input {@link String} to search for matching
     *              phrases
     * @return A {@link Void} so we can use this method in a {@link
     *         Function}
     */
    private Void processInput(String input) {
        // Get the title of the work.
        String title = getTitle(input);

        // Iterate through each phrase we're searching for.
        for (String phrase : mPhrasesToFind) {
            // Check to see how many times (if any) the phrase appears
            // in the input data.
            for (int offset = input.indexOf(phrase);
                 offset != -1;
                 offset = input.indexOf(phrase,
                                        offset + phrase.length())) {

                // Display the results whenever a match is found.
                ThreadJoinTest.display("the phrase \""
                            + phrase
                            + "\" was found at character offset "
                            + offset
                            + " in \""
                            + title
                            + "\"");
            }
        }

        return null;
    }

    /**
     * @return The title portion of the {@code input} {@link String}
     */
    String getTitle(String input) {
        // Create a Matcher.
        Matcher m = Pattern
            // Compile a regex that matches only the first line in the
            // input since each title appears on the first line of the
            // work.
            .compile("(?m)^.*$")

            // Create a matcher for this pattern.
            .matcher(input);

        // Extract the title.
        return m.find()
            // Return the title String if there's a match.
            ? m.group()

            // Return an empty String if there's no match.
            : "";
    }

    /**
     * Display the {@link String} to the output.
     *
     * @param string The {@link String} to display
     */
    private static void display(String string) {
        System.out.println("["
                           + Thread.currentThread().threadId()
                           + "] "
                           + string);
    }
}

