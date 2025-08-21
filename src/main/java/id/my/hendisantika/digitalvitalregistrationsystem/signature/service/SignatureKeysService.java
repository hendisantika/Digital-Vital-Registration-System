package id.my.hendisantika.digitalvitalregistrationsystem.signature.service;

import id.my.hendisantika.digitalvitalregistrationsystem.signature.repository.SignatureKeysRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
}
