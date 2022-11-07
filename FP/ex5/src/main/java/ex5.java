import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This example shows how to sort elements in a collection using a
 * Java anonymous inner class, lambda expression, and method
 * reference.  It also shows how to use the modern Java forEach()
 * method for Streams and collections.
 */
public class ex5 {
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
        // Show how to sort using an anonymous inner class.
        showInnerClass(sArrayCopy.get());

        // Show how to sort using a lambda expression.
        showLambdaExpression(sArrayCopy.get());

        // Show how to sort and print using a method reference and the
        // modern Java Stream forEach() method.
        showMethodReference1(sArrayCopy.get());

        // Show how to sort and print using a method reference and the
        // modern Java Iterable forEach() method.
        showMethodReference2(sArrayCopy.get());

        System.out.println("\nLeaving test");
    }

    /**
     * Show how to sort using an anonymous inner class.
     */
    private static void showInnerClass(String[] nameArray) {
        System.out.println("showInnerClass()");

        // Sort using an anonymous inner class.
        Arrays.sort(nameArray, new Comparator<String>() {
                public int compare(String s, String t) {
                    // Compare the String objects
                    // and ignore the case.
                    return s.compareToIgnoreCase(t);
                }
            });

        // Print out the sorted contents as an array.
        System.out.println(List.of(nameArray));
    }

    /**
     * Show how to sort using a lambda expression.
     */
    private static void showLambdaExpression(String[] nameArray) {
        System.out.println("showLambdaExpression()");

        // Sort using a lambda expression.
        Arrays.sort(nameArray,
                    // Note type deduction here:
                    (s, t) -> s.compareToIgnoreCase(t));

        // Print out the sorted contents as an array.
        System.out.println(List.of(nameArray));
    }

    /**
     * Show how to sort and print using a method reference and the
     * modern Java Stream {@code forEach()} method.
     */
    private static void showMethodReference1(String[] nameArray) {
        System.out.println("showMethodReference1()");

        // Sort using a method reference.
        Arrays.sort(nameArray,
                    String::compareToIgnoreCase);

        // Print out the sorted contents.
        Stream
            // Convert nameArray into a Stream of String objects.
            .of(nameArray)

            // Call printString on each String in the Stream.
            .forEach(ex5::printString);
    }

    /**
     * Show how to sort and print using a method reference and the
     * modern Java Iterable {@code forEach()} method.
     */
    private static void showMethodReference2(String[] nameArray) {
        System.out.println("\nshowMethodReference2()");

        // Sort using a method reference.
        Arrays.sort(nameArray,
                    String::compareToIgnoreCase);

        // Print out the sorted contents.
        List
            // Convert nameArray into a List of String Objects.
            .of(nameArray)

            // Call printString on each String in the Stream.
            .forEach(ex5::printString);
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

