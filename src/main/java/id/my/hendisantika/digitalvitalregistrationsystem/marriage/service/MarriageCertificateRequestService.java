package id.my.hendisantika.digitalvitalregistrationsystem.marriage.service;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
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

import java.io.IOException;
import java.util.Base64;
import java.util.List;
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

}
