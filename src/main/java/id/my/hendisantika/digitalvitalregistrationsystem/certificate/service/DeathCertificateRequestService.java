package id.my.hendisantika.digitalvitalregistrationsystem.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 06.31
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class DeathCertificateRequestService {
    private final CitizenRepository citizenRepository;
    private final DeathCertificateRepository deathCertificateRepository;
    private final DeathCertificateReportService deathCertificateReportService;

}
