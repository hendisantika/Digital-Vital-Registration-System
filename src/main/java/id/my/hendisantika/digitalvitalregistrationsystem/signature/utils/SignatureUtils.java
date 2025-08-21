package id.my.hendisantika.digitalvitalregistrationsystem.signature.utils;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 22/08/25
 * Time: 05.33
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SignatureUtils {
    public static byte[] sha256(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input);
    }

    public static byte[] sha256(String input) throws NoSuchAlgorithmException {
        return sha256(input.getBytes());
    }

    // For signing text messages
    public static BigInteger sign(String message, BigInteger privateKey, BigInteger modulus) throws NoSuchAlgorithmException {
        byte[] hash = sha256(message);
        BigInteger hashed = new BigInteger(1, hash);
        return hashed.modPow(privateKey, modulus);
    }

    //  For signing raw byte data like PDFs
    public static BigInteger sign(byte[] data, BigInteger privateKey, BigInteger modulus) throws NoSuchAlgorithmException {
        byte[] hash = sha256(data);
        BigInteger hashed = new BigInteger(1, hash);
        return hashed.modPow(privateKey, modulus);
    }
}
