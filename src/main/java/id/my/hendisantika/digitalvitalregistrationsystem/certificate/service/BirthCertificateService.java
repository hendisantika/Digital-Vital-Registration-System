package id.my.hendisantika.digitalvitalregistrationsystem.certificate.service;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.BirthCertificateRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 06.25
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BirthCertificateService {
    private final BirthCertificateRepository birthCertificateRepository;
    private final CitizenRepository citizenRepository;
    private final BirthCertificateReportService birthCertificateReportService;
    private final CertificateFileRepository certificateFileRepository;
    private final NotificationService notificationService;
    private final EmailService emailService;
}
