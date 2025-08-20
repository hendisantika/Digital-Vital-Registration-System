package id.my.hendisantika.digitalvitalregistrationsystem.marriage.service;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 21/08/25
 * Time: 06.13
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class MarriageCertificateReportService {
    private final CertificateFileRepository certificateFileRepository;
    private final SignatureKeysService signatureKeysService;
}
