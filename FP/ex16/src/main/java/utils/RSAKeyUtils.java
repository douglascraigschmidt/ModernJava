package utils;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * This Java utility class provides a method to generate a pair of
 * public and private keys.
 */
public class RSAKeyUtils {
    /**
     * A Java utility class should have a private constructor.
     */
    private RSAKeyUtils() {
    }

    /**
     * A Java record to hold a pair of public and private keys.
     */
    public record RSAKeyPair(PublicKey publicKey,
                             PrivateKey privateKey) {
    }

    /**
     * Generate a pair of public and private keys with the given prime
     * numbers.
     *
     * @param prime1 The first prime number
     * @param prime2 The second prime number
     * @return A pair of public and private keys
     * @throws Exception If something goes wrong
     */
    public static RSAKeyPair generateKeyPair(BigInteger prime1,
                                             BigInteger prime2)
        throws Exception {
        // Compute public and private keys using the following
        // algorithm:
        // modulus = prime1 * prime2
        // publicExponent = 65537
        // privateExponent = publicExponent^-1 mod (prime1-1) * (prime2-1)

        // Compute the modulus.
        BigInteger modulus = prime1.multiply(prime2);

        // A commonly used value for the publicExponent.
        BigInteger publicExponent = new BigInteger("65537");

        // Compute the private exponent.
        BigInteger privateExponent = publicExponent
            .modInverse(prime1.subtract(BigInteger.ONE)
                        .multiply(prime2.subtract(BigInteger.ONE)));

        // Create the public and private keys.
        RSAPublicKeySpec publicKeySpec =
            new RSAPublicKeySpec(modulus, publicExponent);
        RSAPrivateKeySpec privateKeySpec
            = new RSAPrivateKeySpec(modulus, privateExponent);

        // Create the key factory.
        KeyFactory keyFactory = KeyFactory
            .getInstance("RSA");

        // Return the pair of public and private keys.
        return new RSAKeyPair
            (keyFactory.generatePublic(publicKeySpec),
             keyFactory.generatePrivate(privateKeySpec));
    }
}
