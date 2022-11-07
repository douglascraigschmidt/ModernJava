/**
 * This example shows how to use modern Java lambda expressions to
 * create a closure, which is a persistent scope that holds on to
 * local variables even after the code execution has moved out of that
 * block, as described at
 * <a href="https://en.wikipedia.org/wiki/Closure_(computer_programming)">this link</a>.
 */
public class ex6 {
    /**
     * This class demonstrates how to implement closures using modern
     * Java.
     */
    static class ClosureExample {
        /**
         * A private field that can be updated by the closure below.
         */
        private int mRes;

        /**
         * This factory method creates a closure that will run in a
         * background thread.
         *
         * @return The background thread reference
         */
        Thread makeThreadClosure(String string, int n) {
            // Create and return a new Thread whose runnable lambda
            // expression defines a closure that reads the method
            // parameters and updates the mRes field.
            return new Thread(() ->
                              System.out.println(string + (mRes += n)));
        }

        /**
         * The constructor creates/starts/runs a thread closure.
         */
        ClosureExample() throws InterruptedException {
            // Create a thread closure.
            Thread t = makeThreadClosure("result = ", 10);

            // Start the thread.
            t.start();

            // Join when the thread is finished.
            t.join();
        }
    }

    /**
     * This method provides the main entry point into this test
     * program.
     */
    static public void main(String[] argv) throws InterruptedException {
        // First demonstrates the closure example.
        new ClosureExample();
    }
}

