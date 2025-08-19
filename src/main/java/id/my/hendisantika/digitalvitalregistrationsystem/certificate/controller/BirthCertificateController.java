package id.my.hendisantika.digitalvitalregistrationsystem.certificate.controller;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.service.BirthCertificateReportService;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.service.BirthCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 06.34
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/certificate")
public class BirthCertificateController {
    private final BirthCertificateService birthCertificateService;
    private final BirthCertificateReportService birthCertificateReportService;
    private final CertificateFileRepository certificateFileRepository;

}
