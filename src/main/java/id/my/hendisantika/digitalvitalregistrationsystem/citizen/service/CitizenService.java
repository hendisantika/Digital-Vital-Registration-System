package id.my.hendisantika.digitalvitalregistrationsystem.citizen.service;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.certificateFile.CertificateFile;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.dto.CitizenRequestDto;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.dto.CitizenResponseDto;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.CitizenStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.Gender;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.mapper.CitizenDtoMapper;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.Citizen;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.repository.CitizenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Notification;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 08.58
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CitizenService {
    private final JwtUtil jwtUtil;

    private final CitizenRepository citizenRepository;
    private final NotificationService notificationService;
    private final CertificateFileRepository certificateFileRepository;


    //  @CacheEvict(value = {"citizenCount", "citizenList","citizenById"}, allEntries = true)
    // @CachePut(value = "citizen", key = "#result.id")
    public CitizenResponseDto createCitizen(CitizenRequestDto citizenRequestDto) {
        User user = jwtUtil.getCurrentUserFromToken();

        // Check if user already has a citizen record
        if (user.getCitizen() != null) {
            throw new RuntimeException("Citizen already exists for this user.");
        }

        Citizen citizen = CitizenDtoMapper.mapToEntity(citizenRequestDto);
        citizen.setUser(user); // 🔗 Link citizen to user

        // 🔽 Store redundant userId and email as well
        citizen.setUserId(user.getId());
        citizen.setUserEmail(user.getEmail());

        Citizen savedCitizen = citizenRepository.save(citizen);
    /*Optional<CertificateFile> certificateFileOptional = certificateFileRepository.findById(savedCitizen.getId());

        Notification.NotificationBuilder notification = Notification.builder()
                .event(NotificationEvent.REVIEWING)
                .channel(DeliveryChannel.EMAIL)
                .type(NotificationType.EMAIL)
                .email(user.getEmail())
                .citizen(savedCitizen)
                .message("Your citizen profile has been successfully submitted and is under review.")
                .createdAt(LocalDateTime.now());
                certificateFileOptional.ifPresent(notification::certificate);

                DocumentService.isUploadCompleted = true;

        notificationService.sendAndDispatch(notification.build());
        log.warn("Citizen created: {}", citizen.getId());*/
        return CitizenDtoMapper.mapToDto(savedCitizen);
    }

    @Cacheable(value = "citizenList")
    public List<CitizenResponseDto> getAllCitizens() {
        return citizenRepository.findAll().stream().map(CitizenDtoMapper::mapToDto).collect(Collectors.toList());
    }

    //@Cacheable(value = "citizenCount")
    public Long getCitizenCount() {
        return citizenRepository.count();
    }

    @CacheEvict(value = "citizenDeleteById", key = "#id")
    public void deleteCitizenById(Long id) {
        citizenRepository.deleteById(id);
    }

    //@CacheEvict(value = "citizenById", key = "#id")
    public CitizenResponseDto updateCitizen(Long id, CitizenRequestDto dto) {
        Citizen citizen = citizenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Citizen not found"));
        dto.setStatus(CitizenStatus.PENDING);

        System.out.println("Updating citizen with: " + dto);  // ✅ Add this
        CitizenDtoMapper.updateEntityFromDto(citizen, dto);

        Citizen updated = citizenRepository.save(citizen);
        System.out.println("Updated citizen: " + updated.getStatus());    // ✅ Add this

        return CitizenDtoMapper.mapToDto(updated);
    }

    // @Cacheable(value = "citizenById", key = "#id")

    public CitizenResponseDto getCitizenById(Long id) {
        Optional<Citizen> citizen = citizenRepository.findById(id);
        if (citizen.isPresent()) {
            return CitizenDtoMapper.mapToDto(citizen.get());
        } else {
            throw new RuntimeException("Citizen not found");
        }
    }

    //@Cacheable(value = "male")
    public Long getMaleCount() {
        return citizenRepository.countByGender(Gender.MALE);
    }

    // @Cacheable(value = "female")
    public Long getFemaleCount() {
        return citizenRepository.countByGender(Gender.FEMALE);
    }

    // @Cacheable(value = "others")
    public Long getOthersCount() {
        return citizenRepository.countByGender(Gender.OTHERS);
    }

    // @Cacheable(value = "approvedList")
    public List<CitizenResponseDto> getApprovedCitizens() {
        return citizenRepository.findAll()
                .stream()
                .filter(cit -> cit.getStatus().equals(CitizenStatus.APPROVED))
                .map(CitizenDtoMapper::mapToDto).collect(Collectors.toList());
    }

    //@Cacheable(value = "pendingList")
    public List<CitizenResponseDto> getPendingCitizens() {
        return citizenRepository.findAll()
                .stream()
                .filter(cit -> cit.getStatus().equals(CitizenStatus.PENDING))
                .map(CitizenDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //  @Cacheable(value = "rejectedList")
    public List<CitizenResponseDto> getRejectedCitizens() {
        return citizenRepository.findAll()
                .stream().filter(citizen -> citizen.getStatus().equals(CitizenStatus.REJECTED))
                .map(CitizenDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void rejectCitizen(Long id, String rejectionReason, Long verifiedBy) {
        Citizen citizen = citizenRepository.findById(id).orElseThrow(() -> new RuntimeException("Citizen not found"));
        citizen.setStatus(CitizenStatus.REJECTED);
        citizen.setReasonForRejection(rejectionReason);
        citizen.setVerifiedBy(verifiedBy);
        citizen.setVerifiedDate(LocalDate.now());
        Citizen savedCitizen = citizenRepository.save(citizen);
        String rejectedHtmlMessage = String.format("""
                            <div style="background-color:#fef2f2; padding: 20px; border: 1px solid #fca5a5; border-radius: 12px; font-family: Arial, sans-serif; color: #991b1b;">
                              <h2 style="margin-top: 0; display: flex; align-items: center; font-size: 22px;">
                                <span style="font-size: 28px; color: #ef4444; margin-right: 10px;">❌</span>
                                Digital Vital Registration
                              </h2>
                              <p>Hello, <strong>%s</strong> 👋</p>
                              <p>We regret to inform you that your citizen profile has been <strong>rejected</strong>.</p>
                              <p>Please visit your local municipality office for further assistance.</p>
                              <p style="margin-top: 12px; background-color: #fee2e2; padding: 12px; border-left: 4px solid #dc2626; font-weight: bold;">
                                📝 Reason: %s
                              </p>
                              <p style="margin-top: 12px; font-size: 14px; color: #7f1d1d;">
                                🕒 <strong>Rejected on:</strong> %s
                              </p>
                            </div>
                        """,
                citizen.getFirstName(),
                rejectionReason,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a"))
        );

        Notification.NotificationBuilder notification = Notification.builder()
                .event(NotificationEvent.VERIFICATION_REJECTED)
                .channel(DeliveryChannel.EMAIL)
                .type(NotificationType.EMAIL)
                .email(savedCitizen.getUserEmail())
                .citizen(savedCitizen)
                .message(rejectedHtmlMessage)
                .createdAt(LocalDateTime.now());


        notificationService.sendAndDispatch(notification.build());
        log.warn("Citizen rejected: {}", savedCitizen.getId());
    }

    @Transactional
    public void approveCitizen(Long id, Long verifiedBy) {
        Citizen citizen = citizenRepository.findById(id).orElseThrow(() -> new RuntimeException("Citizen not found"));
        citizen.setStatus(CitizenStatus.APPROVED);
        citizen.setVerifiedBy(verifiedBy);
        citizen.setReasonForRejection(null);
        citizen.setVerifiedDate(LocalDate.now());


        String approvedHtmlMessage = String.format("""
                            <div style="background-color:#f0fdf4;padding:20px;border:1px solid #34d399;border-radius:12px;font-family:Arial,sans-serif;color:#064e3b;">
                              <h2 style="margin-top:0;display:flex;align-items:center;font-size:22px;">
                                <span style="font-size:28px;color:#10b981;margin-right:10px;">✔️</span>
                                Digital Vital Registration
                              </h2>
                              <p>Hello, <strong>%s</strong> 👋</p>
                              <p>We are pleased to inform you that your citizen profile has been <strong>approved</strong> ✅ by the verification authority.</p>
                              <p style="margin-top:12px;font-size:14px;color:#065f46;">
                                🕒 <strong>Approved on:</strong> %s
                              </p>
                            </div>
                        """,
                citizen.getFirstName(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a"))
        );

        Citizen savedCitizen = citizenRepository.save(citizen);
        Optional<CertificateFile> certificateFileOptional = certificateFileRepository.findByCitizen_Id(id).stream().findFirst();

        Notification.NotificationBuilder notification = Notification.builder()
                .event(NotificationEvent.VERIFICATION_APPROVED)
                .channel(DeliveryChannel.EMAIL)
                .type(NotificationType.EMAIL)
                .email(savedCitizen.getUserEmail())
                .citizen(savedCitizen)
                .message(approvedHtmlMessage)
                .createdAt(LocalDateTime.now());
        certificateFileOptional.ifPresent(notification::certificate);


        notificationService.sendAndDispatch(notification.build());
        log.warn("Citizen created: {}", savedCitizen.getId());
    }
}
