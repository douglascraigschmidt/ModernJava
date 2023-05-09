import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This example shows how to sort elements in a collection using Java
 * method references.  It also shows how to use the modern Java
 * forEach() method for Streams and collections.
 */
public class ex7 {
    /**
     * The array to sort and print.
     */
    private static final String[] sNameArray = {
        "Barbara",
        "James",
        "Mary",
        "John",
        "Robert",
        "Michael",
        "Linda",
        "james",
        "mary"
    };

    /**
     * This {@link Supplier} makes a copy of an array.
     */
    private static final Supplier<String[]> sArrayCopy =
        () -> Arrays.copyOf(sNameArray, sNameArray.length);

    /**
     * This method provides the main entry point into this test
     * program.
     */
    static public void main(String[] argv) throws InterruptedException {
        System.out.println("Entering test");

        // Demonstrate various techniques for sorting/printing an
        // array.
        System.out.println("Original array:\n"
                           + List.of(sNameArray));

        // Show how to sort and print using a method reference and the
        // modern Java Stream forEach() method.
        sortMethodReference1(sArrayCopy.get());

        // Show how to sort and print using a method reference and the
        // modern Java Iterable forEach() method.
        sortMethodReference2(sArrayCopy.get());

        System.out.println("\nLeaving test");
    }

    /**
     * Show how to sort and print using a method reference and the
     * modern Java Stream {@code forEach()} method.
     */
    private static void sortMethodReference1(String[] nameArray) {
        System.out.println("sortMethodReference1()");

        // Sort using a method reference.
        Arrays.sort(nameArray,
                    String::compareToIgnoreCase);

        // Print out the sorted contents.
        Stream
            // Convert nameArray into a Stream of String objects.
            .of(nameArray)

            // Call printString on each String in the Stream.
            .forEach(ex14::printString);
    }

    /**
     * Show how to sort and print using a method reference and the
     * modern Java Iterable {@code forEach()} method.
     */
    private static void sortMethodReference2(String[] nameArray) {
        System.out.println("\nsortMethodReference2()");

        // Sort using a method reference.
        Arrays.sort(nameArray,
                    String::compareToIgnoreCase);

        // Print out the sorted contents.
        List
            // Convert nameArray into a List of String Objects.
            .of(nameArray)

            // Call printString on each String in the Stream.
            .forEach(ex14::printString);
    }

    /**
     * Prints {@link String} s with a " " appended.
     *
     * @param s The {@link String} to print
     */
    private static void printString(String s) {
        System.out.print(s + " ");
    }
}

