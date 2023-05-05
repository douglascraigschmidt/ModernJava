import java.util.*;
import java.util.function.Predicate;

/**
 * This example shows the use of predicate lambda expressions in the
 * context of the Java {@link HashMap} {@code removeIf()} method.
 */
public class ex7 {
    /**
     * This factory method creates a {@link HashMap} containing the
     * names of Stooges and their IQs.
     */
    static private Map<String, Integer> makeMap() {
       return new HashMap<String, Integer>()  {
          {
            put("Larry", 100);
            put("Curly", 90);
            put("Moe", 110);
          }
        };
    }

    /**
     * Demonstrate the use of predicate lambda expressions.
     */
    static public void main(String[] argv) {
        // Create a map that associates Stooges with their IQs.
        Map<String, Integer> stooges = makeMap();

        System.out.println(stooges);

        // This lambda expression removes entries with IQ less than or
        // equal to 100.
        stooges.entrySet().removeIf(entry -> entry.getValue() <= 100);
        System.out.println(stooges);

        // Create another map that associates Stooges with their IQs.
        stooges = makeMap();
        System.out.println(stooges);

        // Create two predicate objects.
        Predicate<Map.Entry<String, Integer>> iq =
            entry -> entry.getValue() <= 100;

        Predicate<Map.Entry<String, Integer>> curly = 
            entry -> entry.getKey().equals("Curly");

        // This lambda expression removes entries with IQ less than or
        // equal to 100 with the name "curly".
        stooges.entrySet().removeIf(iq.and(curly));

        // Print the updated Map.
        System.out.println(stooges);
    }
}

