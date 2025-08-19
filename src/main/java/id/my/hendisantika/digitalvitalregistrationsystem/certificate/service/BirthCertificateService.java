package id.my.hendisantika.digitalvitalregistrationsystem.certificate.service;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.certificateFile.CertificateFile;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.enums.CertificateStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.model.BirthCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.BirthCertificateRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.management.Notification;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public BirthCertificateRequest saveCertificate(BirthCertificateRequest birthCertificate) {
        boolean exists = birthCertificateRepository.existsByChildNameAndDateOfBirthAndCitizen(birthCertificate.getChildName(), birthCertificate.getDateOfBirth(), birthCertificate.getCitizen());
        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Certificate already exists for the given child name and date of birth");
        }
        if (birthCertificate.getCitizen() == null || birthCertificate.getCitizen().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Citizen ID is missing in request");
        }


        Long citizenId = birthCertificate.getCitizen().getId();

        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Citizen not found with ID: " + citizenId));

        birthCertificate.setCitizen(citizen);
        birthCertificate.setStatus(CertificateStatus.PENDING);
        birthCertificate.setRequestedBy(citizen.getId());
        birthCertificate.setRequestedAt(LocalDate.now());
        birthCertificate.setMunicipality(birthCertificate.getMunicipality());
        birthCertificate.setDistrict(birthCertificate.getDistrict());
        birthCertificate.setNationality(birthCertificate.getNationality());

        BirthCertificateRequest savedRequest = birthCertificateRepository.save(birthCertificate);


        Notification.NotificationBuilder notification = Notification.builder()
                .event(NotificationEvent.REVIEWING)
                .channel(DeliveryChannel.EMAIL)
                .type(NotificationType.EMAIL)
                .email(savedRequest.getCitizen().getUserEmail())
                .citizen(citizen)
                .message("Dear, " + citizen.getFirstName() + "Your Birth Certificate has been successfully submitted and is under review.")
                .createdAt(LocalDateTime.now());


        notificationService.sendAndDispatch(notification.build());
        return savedRequest;
    }

    public CertificateFile getCertificateByReferenceNumber(String referenceNumber) {
        return certificateFileRepository.findByReferenceNumber(referenceNumber);
    }

    public CertificateFile generateBirthCertificateReport(Long id) {
        BirthCertificateRequest cert = birthCertificateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found"));

        Citizen citizen = cert.getCitizen();

        Map<String, Object> params = new HashMap<>();
        params.put("childName", cert.getChildName());
        params.put("gender", cert.getGender());
        params.put("dateOfBirth", cert.getDateOfBirth());
        params.put("birthPlace", citizen.getMunicipality());
        params.put("firstName", citizen.getFirstName());
        params.put("middleName", citizen.getMiddleName());
        params.put("lastName", citizen.getLastName());
        params.put("spouseName", citizen.getSpouseName());
        params.put("district", citizen.getDistrict());
        params.put("municipality", citizen.getMunicipality());
        params.put("wardNo", citizen.getWardNo());
        params.put("tole", citizen.getTole());
        params.put("nationality", citizen.getNationality());
        params.put("verifiedBy", "Ward Secretary");
        params.put("verifiedAt", citizen.getMunicipality());
        params.put("issuedDate", LocalDate.now());

        CertificateFile file = birthCertificateReportService.generateBirthCertificateReport(params, citizen, cert);

        cert.setCertificateFile(file);
        birthCertificateRepository.save(cert); // must save this relation

        params.put("referenceNumber", file.getReferenceNumber());

        return file;
    }

    public Long countBirthCertificateRequests() {
        return birthCertificateRepository.count();
    }

    public void deleteBirthCertificateRequestById(Long id) {
        if (!birthCertificateRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found with ID: " + id);

        }
        birthCertificateRepository.deleteById(id);
    }

    public Long countPendingRequest() {
        return birthCertificateRepository.findAll()
                .stream().filter(cert -> cert.getStatus() == CertificateStatus.PENDING).count();
    }

    public Long countApprovedRequest() {
        return certificateFileRepository.count();
    }

    public List<BirthCertificateRequest> getAllRequests() {
        return birthCertificateRepository.findAll().stream().toList();
    }

    public Long countRejectedRequest() {
        return birthCertificateRepository.findAll()
                .stream()
                .filter(cert -> cert.getStatus() == CertificateStatus.REJECTED)
                .count();
    }

    public List<BirthCertificateRequest> getRequestByCitizenId(Long id) {
        return birthCertificateRepository.findByCitizenId(id);
    }
}
