import utils.BardDataFactory;
import utils.Options;

import static java.util.AbstractMap.SimpleEntry;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import static utils.TextUtils.getTitle;
import static utils.TextUtils.match;

/**
 * This program implements an "embarrassingly parallel" app that
 * searches for phrases in a {@link List} of {@link String} objects,
 * each containing a different work of William Shakespeare.  It
 * demonstrates the use of modern Java function programming features
 * (such as lambda expressions, method references, and functional
 * interfaces) in conjunction with the Java parallel streams framework.
 * 
 * This implementation requires no Java synchronization mechanisms
 * other than what's provided by the Java parallel streams framework.
 */
public class BardStreamTest
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
    private static final List<String> mInputList = BardDataFactory
        .getInput(sSHAKESPEARE_DATA_FILE, "@");

    /**
     * The {@link List} of phrases to search for in the works.
     */
    private static final List<String> mPhrasesToFind = BardDataFactory
        .getPhraseList(sPHRASE_LIST_FILE);

    /**
     * This is the main entry point into the program.
     */
    static public void main(String[] args) {
        System.out.println("Starting BardStreamTest");

        Options.instance().parseArgs(args);

        // Create/run an object to search for phrases in parallel.
        new BardStreamTest().run();

        System.out.println("Ending BardStreamTest");
    }

    /**
     * Create a Java parallel stream to perform the concurrent Bard
     * phrase searches.
     */
    @Override
    public void run() {
        StreamSupport
            // Convert the List to a sequential or parallel Stream.
            .stream(mInputList.spliterator(),
                    Options.instance().getParallel())

            // Process each work of Shakespeare.
            .forEach(this::processInput);
    }

    /**
     * This method runs in a background {@link Thread} in the common
     * fork-join pool and uses the {@code input} for all occurrences
     * of the Bard phrases to find.
     *
     * @param input Input {@link String} to search for matching
     *              phrases
     */
    private void processInput(String input) {
        // Get the title of the work.
        var title = getTitle(input);

        StreamSupport
            // Convert the List to a sequential or parallel Stream.
            .stream(mPhrasesToFind.spliterator(),
                    Options.instance().getParallel())

            // Create a Stream of matching phrases.
            .flatMap(phrase ->
                match(phrase, input))

            // Print all matches.
            .forEach(entry ->
                     displayMatch(title, entry));
    }

    /**
     * Display the {@code entry}
     * @param title The title of the work
     * @param entry The {@link SimpleEntry} of phrase and offset
     */
    private static void displayMatch
        (String title,
         SimpleEntry<String, Integer> entry) {
        BardStreamTest.display("\""
                               + entry.getKey()
                               + "\" appears at offset "
                               + entry.getValue()
                               + " in \""
                               + title
                               + "\"");
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

