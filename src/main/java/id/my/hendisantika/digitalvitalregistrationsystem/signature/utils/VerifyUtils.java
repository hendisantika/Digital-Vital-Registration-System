package id.my.hendisantika.digitalvitalregistrationsystem.signature.utils;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 22/08/25
 * Time: 05.34
 * To change this template use File | Settings | File Templates.
 */
@Service
public class VerifyUtils {
    public static boolean verifySignature(byte[] data, BigInteger signature, BigInteger publicKey, BigInteger modulus) throws NoSuchAlgorithmException {
        byte[] hash = SignatureUtils.sha256(data);
        BigInteger hashed = new BigInteger(1, hash);

        BigInteger decryptedSignature = signature.modPow(publicKey, modulus);
        return hashed.equals(decryptedSignature);
    }

    public static boolean verifySignature(String message, BigInteger signature, BigInteger publicKey, BigInteger modulus) throws NoSuchAlgorithmException {
        byte[] hash = SignatureUtils.sha256(message);
        BigInteger hashed = new BigInteger(1, hash);

        BigInteger decryptedSignature = signature.modPow(publicKey, modulus);
        return hashed.equals(decryptedSignature);
    }
}
