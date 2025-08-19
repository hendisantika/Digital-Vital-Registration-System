package id.my.hendisantika.digitalvitalregistrationsystem.certificate.service;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.enums.CertificateStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.model.DeathCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.DeathCertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

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

    public DeathCertificateRequest saveDeathCertificateRequest(DeathCertificateRequest deathCertificateRequest) {
        if (deathCertificateRepository.existsByDeceasedNameAndDateOfBirthAndDeceasedDateAndRequestedBy(deathCertificateRequest.getDeceasedName(), deathCertificateRequest.getDateOfBirth(),
                deathCertificateRequest.getDeceasedDate(), deathCertificateRequest.getRequestedBy())) {
            throw new RuntimeException("Death certificate already exists");
        }

        Long citizenId = deathCertificateRequest.getRequestedBy().getId();
        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Citizen not found with ID: " + citizenId));
        deathCertificateRequest.setRequestedBy(citizen);
        deathCertificateRequest.setCertificateStatus(CertificateStatus.PENDING);
        deathCertificateRequest.setRequestedAt(LocalDateTime.now());


        return deathCertificateRepository.save(deathCertificateRequest);
    }
}
