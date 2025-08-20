package id.my.hendisantika.digitalvitalregistrationsystem.citizen.service;

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
}
