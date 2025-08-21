package id.my.hendisantika.digitalvitalregistrationsystem.signature.service;

import org.aspectj.weaver.SignatureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Time: 05.30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DigitalSignatureService {

    private static final Logger LOG = LoggerFactory.getLogger(DigitalSignatureService.class);
    private final SignatureKeysService signatureKeysService;

    // New version: sign raw bytes
    public String signCertificate(byte[] certificateBytes) throws NoSuchAlgorithmException {
        BigInteger message = new BigInteger(1, certificateBytes); // Avoid negative numbers
        BigInteger signature = SignatureUtils.sign(message.toByteArray(), signatureKeysService.getPrivateKey(), signatureKeysService.getModulus());
        return signature.toString();
    }

    // New version: verify raw bytes
    public boolean verifyCertificate(byte[] uploadedBytes, String signature) throws NoSuchAlgorithmException {
        BigInteger sig = new BigInteger(signature);
        return VerifyUtils.verifySignature(uploadedBytes, sig, signatureKeysService.getPublicKey(), signatureKeysService.getModulus());
    }
}
