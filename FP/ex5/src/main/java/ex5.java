import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This example shows how to sort elements in a collection using a
 * Java anonymous inner class and a lambda expression.
 */
@SuppressWarnings("Convert2MethodRef")
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
        sortInnerClass(sArrayCopy.get());

        // Show how to sort using a lambda expression.
        sortLambdaExpression(sArrayCopy.get());

        System.out.println("\nLeaving test");
    }

    /**
     * Show how to sort using an anonymous inner class.
     */
    private static void sortInnerClass(String[] nameArray) {
        System.out.println("sortInnerClass()");

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
    private static void sortLambdaExpression(String[] nameArray) {
        System.out.println("sortLambdaExpression()");

        // Sort using a lambda expression.
        Arrays.sort(nameArray,
                    // Note type deduction here:
                    (s, t) -> s.compareToIgnoreCase(t));

        // Print out the sorted contents as an array.
        System.out.println(List.of(nameArray));
    }

    /**
     * Prints {@link String} s with " " appended.
     *
     * @param s The {@link String} to print
     */
    private static void printString(String s) {
        System.out.print(s + " ");
    }
}

