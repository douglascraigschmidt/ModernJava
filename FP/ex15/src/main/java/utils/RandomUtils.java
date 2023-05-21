package utils;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * This Java utility class contains static method(s) that generate
 * random numbers.
 */
public final class RandomUtils {
    /**
     * A Java utility class should have a private constructor.
     */
    private RandomUtils() {}

    /**
     * Generate a {@link List} of random {@link Integer} objects used
     * to check primality.
     */
    public static List<Integer> generateRandomNumbers(int count, 
                                                      int maxValue) {
        // Create a new Random object.
        Random rand = new Random();

        // Initialize a new ArrayList to store the random Integer
        // objects.
        List<Integer> list = new ArrayList<>();

        // Iterate for 'count' times to generate the required number
        // of random Integer objects.
        for(int i = 0; i < count; i++) {

            // Generate a random number within the specified range
            // (maxValue - count) to maxValue.
            int randomNumber = rand
                // Generate a random Integer in the range 0 to count
                // exclusive.
                .nextInt(count)

                // Shift the range of generated numbers from 0 to
                // count to (maxValue - count) to maxValue.
                + (maxValue - count);

            // Add the generated random Integer to the List.
            list.add(randomNumber);
        }

        // Return the list of random integers
        return list;
    }
}
