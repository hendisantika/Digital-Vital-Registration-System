package id.my.hendisantika.digitalvitalregistrationsystem.certificate.service;

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
}
