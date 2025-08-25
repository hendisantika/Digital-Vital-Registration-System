package id.my.hendisantika.digitalvitalregistrationsystem.signature.KeyGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 22/08/25
 * Time: 05.27
 * To change this template use File | Settings | File Templates.
 */
public class RSAKeyGenerator {
    public static final Logger LOGGER = LoggerFactory.getLogger(RSAKeyGenerator.class);
    private static final SecureRandom random = new SecureRandom();

    private BigInteger p; // First prime number
    private BigInteger q; // Second prime number
    private BigInteger n; // Modulus (n = p * q)
    private BigInteger e; // Public exponent
    private BigInteger d; // Private exponent

    public RSAKeyGenerator() {
        generateKeys(2048);
    }

    public RSAKeyGenerator(int bitLength) {
        generateKeys(bitLength);
    }

    private void generateKeys(int bitLength) {
        // Generate two prime numbers
        p = BigInteger.probablePrime(bitLength / 2, random);
        q = BigInteger.probablePrime(bitLength / 2, random);

        // Calculate n = p * q
        n = p.multiply(q);

        // Calculate phi(n) = (p-1) * (q-1)
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Choose e = 65537 (commonly used public exponent)
        e = new BigInteger("65537");

        // Calculate d = e^-1 mod phi(n)
        d = e.modInverse(phi);

        LOGGER.info("RSA keys generated with bit length: {}", bitLength);
    }

    public BigInteger getPrivateKey() {
        return d;
    }

    public BigInteger getPublicKey() {
        return e;
    }

    public BigInteger getModulus() {
        return n;
    }
}
