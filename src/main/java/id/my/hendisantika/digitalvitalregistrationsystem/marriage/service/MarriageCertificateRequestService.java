package id.my.hendisantika.digitalvitalregistrationsystem.marriage.service;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.certificateFile.CertificateFile;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.enums.CertificateStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.Citizen;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.repository.CitizenDocumentRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.repository.CitizenRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.dto.MarriageCertificateResponseDto;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.mapper.MarriageCertificateRequestMapper;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.model.MarriageCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.repository.ForeignPersonRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.repository.MarriageCertificateRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.Notification;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 21/08/25
 * Time: 06.14
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MarriageCertificateRequestService {
    private final MarriageCertificateRequestRepository marriageCertificateRequestRepository;
    private final CitizenRepository citizenRepository;
    private final NotificationService notificationService;
    private final CertificateFileRepository certificateFileRepository;
    private final ForeignPersonRepository foreignPersonRepository;
    private final CitizenDocumentRepository citizenDocumentRepository;
    private final MarriageCertificateReportService marriageCertificateReportService;

    public MarriageCertificateRequest save(MarriageCertificateRequest request,
                                           MultipartFile marriagePhoto,
                                           MultipartFile wardOfficeFile) {
        try {
            if (marriagePhoto != null && !marriagePhoto.isEmpty()) {
                request.setMarriagePhotoData(Base64.getEncoder().encodeToString(marriagePhoto.getBytes()));
                request.setMarriagePhotoFileName(marriagePhoto.getOriginalFilename());
            }

            if (wardOfficeFile != null && !wardOfficeFile.isEmpty()) {
                request.setWardOfficeFileData(Base64.getEncoder().encodeToString(wardOfficeFile.getBytes()));
                request.setWardOfficeFileName(wardOfficeFile.getOriginalFilename());
            }

            return marriageCertificateRequestRepository.save(request);

        } catch (IOException e) {
            throw new RuntimeException("File processing failed: " + e.getMessage());
        }
    }

    public List<MarriageCertificateResponseDto> getAllRequest() {
        return marriageCertificateRequestRepository.findAll().stream().map(MarriageCertificateRequestMapper::toMarriageCertificateResponseDto).collect(Collectors.toList());
    }

    public MarriageCertificateResponseDto getById(Long id) {
        return marriageCertificateRequestRepository.findByRequestedById(id).stream().map(MarriageCertificateRequestMapper::toMarriageCertificateResponseDto).findFirst().orElse(null);
    }

    public boolean existsByRequestId(Long id) {
        return marriageCertificateRequestRepository.existsByRequestedById(id);
    }

    public void sendVideoVerificationLink(Long id) {
        MarriageCertificateRequest marriageCertificateRequest = marriageCertificateRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find marriage certificate request with id: " + id));

        // Generate the Jitsi meeting link
        String meetingLink = "https://meet.jit.si/marriagerequest-" + UUID.randomUUID();
        String scheduledTime = LocalDateTime.now().plusHours(24).format(DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a"));

        // Set status and meeting link
        marriageCertificateRequest.setStatus(CertificateStatus.PENDING_VIDEO_CALL_VERIFICATION);
        marriageCertificateRequest.setVideoVerificationLink(meetingLink);
        marriageCertificateRequest.setScheduledTime(scheduledTime);
        MarriageCertificateRequest savedRequest = marriageCertificateRequestRepository.save(marriageCertificateRequest);

        // Get citizen who requested
        Citizen citizen = citizenRepository.findById(marriageCertificateRequest.getRequestedBy().getId())
                .orElseThrow(() -> new RuntimeException("Could not find citizen with id: " + id));

        // Define a time (could be scheduled or current + 1 hour)

        // Compose the email message
        String emailMessage = String.format(
                "Dear %s,%s,\n\n" +
                        "Your marriage certificate request is under review. A video verification call has been scheduled.\n\n" +
                        "📅 Scheduled Time: %s\n" +
                        "🔗 Video Link: %s\n\n" +
                        "Please ensure both you and your partner are present with the following documents:\n" +
                        "1. Citizenship certificate\n" +
                        "2. Passport (if foreign partner)\n" +
                        "3. Ward office recommendation\n" +
                        "4. Marriage photo\n\n" +
                        "Please join the video call 5 minutes early. Ensure your camera and microphone are working.\n\n" +
                        "Best regards,\nSmart Municipality Services",
                citizen.getFirstName(), citizen.getLastName(),
                scheduledTime,
                meetingLink
        );

        // Build and send the notification
        Notification notification = Notification.builder()
                .event(NotificationEvent.REVIEWING)
                .channel(DeliveryChannel.EMAIL)
                .type(NotificationType.EMAIL)
                .email(marriageCertificateRequest.getRequestedBy().getUserEmail())
                .citizen(citizen)
                .message(emailMessage)
                .createdAt(LocalDateTime.now())
                .build();

        notificationService.sendAndDispatch(notification);
    }

    public void approve(Long id) {
        MarriageCertificateRequest marriageCertificateRequest = marriageCertificateRequestRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find marriage certificate request with id: " + id));

        marriageCertificateRequest.setStatus(CertificateStatus.APPROVED);
        marriageCertificateRequest.setVerifiedAt(LocalDate.now());
        MarriageCertificateRequest savedRequest = marriageCertificateRequestRepository.save(marriageCertificateRequest);

        CertificateFile file = certificateFileRepository.findByMarriageCertificateRequestId(id)
                .orElseGet(() -> generateMarriageCertificate(id));

        Citizen citizen = citizenRepository
                .findById(savedRequest.getRequestedBy().getId())
                .orElseThrow(() -> new RuntimeException("Could not find citizen with id: " + savedRequest.getRequestedBy().getId()));

        // Prepare email recipients
        List<String> recipients = new ArrayList<>();
        if (savedRequest.getRequestedBy().getUserEmail() != null) {
            recipients.add(savedRequest.getRequestedBy().getUserEmail());
        }

        if (savedRequest.getForeignPartner() != null && savedRequest.getForeignPartner().getEmail() != null) {
            recipients.add(savedRequest.getForeignPartner().getEmail());
        }

        // Prepare message and subject
        String subject = "🎉 Congratulations! Your Marriage Certificate Has Been Approved";
        String message = String.format(
                """
                         Congratulations! Your Marriage Certificate Has Been Approved\s
                        
                        Dear %s,
                        
                        We are pleased to inform you that your marriage certificate request has been approved.
                        You can find the approved certificate attached to this email.
                        
                        If you have any questions or need further assistance, feel free to contact your municipality office.
                        
                        Warm regards,
                        Smart Municipality Team""",
                citizen.getFirstName()
        );

        // Send email with attachment to all recipients (assuming your emailService supports multi-recipient sending)
        // emailService.sendEmailWithAttachment(recipients, subject, message, file.getFilePath());

        // Send notification to main citizen
        Notification notification = Notification.builder()
                .event(NotificationEvent.VERIFICATION_APPROVED)
                .channel(DeliveryChannel.BOTH)
                .type(NotificationType.EMAIL)
                .email(citizen.getUserEmail())
                .citizen(citizen)
                .message(message)
                .createdAt(LocalDateTime.now())
                .certificate(file)
                .certificateId(file.getId())
                .build();

        notificationService.sendAndDispatch(notification);
    }

    public void reject(Long id) {
        MarriageCertificateRequest marriageCertificateRequest = marriageCertificateRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find marriage certificate request with id: " + id));
        marriageCertificateRequest.setStatus(CertificateStatus.REJECTED);
        marriageCertificateRequestRepository.save(marriageCertificateRequest);
    }

    public Long countMarriageCertificateRequests() {
        return marriageCertificateRequestRepository.count();
    }

    public Map<String, Long> countMarriageByMonth() {
        return marriageCertificateRequestRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(req -> req.getRequestedAt().getMonth().name(), Collectors.counting()));
    }

    public MarriageCertificateRequest getRequestById(Long id) {
        return marriageCertificateRequestRepository.findById(id).orElse(null);
    }
}
