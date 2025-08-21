package id.my.hendisantika.digitalvitalregistrationsystem.signature.KeyGenerator;

import org.bouncycastle.util.test.FixedSecureRandom;
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

    public BigInteger p; // First prime number
    public BigInteger q; // Second prime number
    public BigInteger n; // Modulus (n = p * q)
    public BigInteger e; // Public exponent
    public FixedSecureRandom.BigInteger d; // Private exponent
}
