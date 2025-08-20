package id.my.hendisantika.digitalvitalregistrationsystem.dashboard;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.service.BirthCertificateService;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.service.DeathCertificateRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 21/08/25
 * Time: 05.54
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashboard")
public class DashboardAnalysis {
    private final DeathCertificateRequestService deathCertificateRequestService;
    private final BirthCertificateService birthCertificateService;
    private final MarriageCertificateRequestService marriageCertificateRequestService;

    @GetMapping("/birth/count-by-month")
    public ResponseEntity<Map<String, Long>> getBirthCountByMonth() {
        return ResponseEntity.ok().body(birthCertificateService.countBirthByMonth());
    }
}
