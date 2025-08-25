package id.my.hendisantika.digitalvitalregistrationsystem.signature.service;

import id.my.hendisantika.digitalvitalregistrationsystem.signature.KeyGenerator.RSAKeyGenerator;
import id.my.hendisantika.digitalvitalregistrationsystem.signature.model.SignatureKeys;
import id.my.hendisantika.digitalvitalregistrationsystem.signature.repository.SignatureKeysRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 22/08/25
 * Time: 05.31
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class SignatureKeysService {
    private static final Logger LOG = LoggerFactory.getLogger(SignatureKeysService.class);
    private final SignatureKeysRepository signatureKeysRepository;

    @PostConstruct
    public void generateAndStoreKeysIfNotExists() {
        if (signatureKeysRepository.count() == 0) {
            RSAKeyGenerator rsa = new RSAKeyGenerator(2048);
            SignatureKeys keys = SignatureKeys.builder()
                    .privateKey(rsa.getPrivateKey().toString())
                    .publicKey(rsa.getPublicKey().toString())
                    .modulus(rsa.getModulus().toString())
                    .build();
            signatureKeysRepository.save(keys);
        }
    }

    private SignatureKeys getLatestKeys() {
        return signatureKeysRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new RuntimeException("No signature keys found"));
    }

    @Cacheable(value = "publicKey")
    public BigInteger getPublicKey() {
        return new BigInteger(getLatestKeys().getPublicKey());
    }

    @Cacheable(value = "modulus")
    public BigInteger getModulus() {
        return new BigInteger(getLatestKeys().getModulus());
    }

    @Cacheable(value = "privateKey")
    public BigInteger getPrivateKey() {
        return new BigInteger(getLatestKeys().getPrivateKey());
    }
}
