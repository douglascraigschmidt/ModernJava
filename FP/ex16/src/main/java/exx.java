import utils.ActiveObject;
import utils.PrimeUtils;
import utils.RSAKeyUtils;

import static utils.PrimeUtils.generateProbablePrime;

/**
 * This example shows how to use modern Java lambda expressions to
 * create an Active Object that's implemented via a virtual {@link
 * Thread} closure.  This Active Object is used to check primality of
 * large numbers as part of computing RSA public and private keys.
 *
 * A closure is a persistent scope that holds on to local variables
 * even after the code execution has moved out of that block, as
 * described at <a
 * href="https://en.wikipedia.org/wiki/Closure_(computer_programming)">this
 * link</a>.
 */
public class exx {
    /**
     * This method provides the main entry point into this test
     * program.
     */
    static public void main(String[] argv) throws Exception {
        System.out.println("Entering ActiveObjectPrimeTest");

        // Create two Active Objects that concurrently check the
        // primality of large numbers in separate threads.
        var checkPrimality1 =
            new ActiveObject<>(PrimeUtils::checkIfPrime,
                               generateProbablePrime(1024));

        var checkPrimality2 =
            new ActiveObject<>(PrimeUtils::checkIfPrime,
                               generateProbablePrime(1024));

        // Get the results of both primality checks, blocking until
        // the results are available. The results are returned in the
        // order the threads in the closures were started.
        var primeResult1 = checkPrimality1.get();
        var primeResult2 = checkPrimality2.get();

        // Print the results.
        System.out.println("The value "
                           + primeResult1.getKey()
                           + "\nis "
                           + (primeResult1.getValue() ? "" : "not ")
                           + "prime and the value\n"
                           + primeResult2.getKey()
                           + "\nis "
                           + (primeResult2.getValue() ? "" : "not ")
                           + "prime.");

        if (primeResult1.getValue()
            && primeResult2.getValue()) {
            // Create a new RSA key pair.
            var keyPair = RSAKeyUtils
                .generateKeyPair(primeResult1.getKey(),
                                 primeResult2.getKey());

            // Print the public and private keys.
            System.out.println("Public key = "
                               + keyPair.publicKey().toString()
                               + "\nPrivate key = "
                               + keyPair.privateKey().toString());
        } else
            System.out.println("Numbers are not both prime");

        System.out.println("Leaving ActiveObjectPrimeTest");
    }
}

