import utils.RSAKeyUtils;

import static utils.PrimeUtils.generateProbablePrime;

/**
 * This example shows how to use modern Java lambda expressions to
 * create {@link Thread} closures that are used to check primality of
 * large numbers as part of computing RSA public and private keys.  A
 * closure is a persistent scope that holds on to local variables even
 * after the code execution has moved out of a block.
 */
public class ex6 {
    /**
     * This method provides the main entry point into this test
     * program.
     */
    static public void main(String[] argv) throws Exception {
        System.out.println("Entering ClosurePrimeTest");

        // Create two closures that concurrently check the primality
        // of large numbers in separate threads.
        var  checkPrimality1 =
            new CheckPrimality(generateProbablePrime(1024))
                .start();

        var checkPrimality2 =
            new CheckPrimality(generateProbablePrime(1024))
                .start();

        // Get the results of both primality checks, blocking until
        // the results are available. The results are returned in the
        // order the threads in the closures were started.
        var primeResult1 = checkPrimality1.getResult();
        var primeResult2 = checkPrimality2.getResult();

        // Print the results.
        System.out.println("The value "
            + primeResult1.primeCandidate()
            + "\nis "
            + (primeResult1.isPrime() ? "" : "not ")
            + "prime and the value\n"
            + primeResult2.primeCandidate()
            + "\nis "
            + (primeResult2.isPrime() ? "" : "not ")
            + "prime.");

        if (primeResult1.isPrime() 
            && primeResult2.isPrime()) {
            // Create a new RSA key pair.
            var keyPair = RSAKeyUtils
                .generateKeyPair(primeResult1.primeCandidate(),
                                 primeResult2.primeCandidate());

            // Print the public and private keys.
            System.out.println("Public key = "
                               + keyPair.publicKey().toString()
                               + "\nPrivate key = "
                               + keyPair.privateKey().toString());
        } else
            System.out.println("Numbers are not both prime");

        System.out.println("Leaving ClosurePrimeTest");
    }
}

