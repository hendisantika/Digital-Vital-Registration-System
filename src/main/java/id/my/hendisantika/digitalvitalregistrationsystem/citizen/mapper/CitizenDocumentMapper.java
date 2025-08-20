package id.my.hendisantika.digitalvitalregistrationsystem.citizen.mapper;

import id.my.hendisantika.digitalvitalregistrationsystem.citizen.dto.CitizenDocumentRequestDto;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.dto.CitizenDocumentResponseDto;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.Citizen;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.CitizenDocument;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 08.55
 * To change this template use File | Settings | File Templates.
 */
public class CitizenDocumentMapper {
    public static CitizenDocumentResponseDto toResponseDto(CitizenDocument document) {
        return CitizenDocumentResponseDto.builder()
                .id(document.getId())
                .fileName(document.getFileName())
                .documentType(document.getDocumentType())
                .updatedAt(document.getUploadDate())
                .citizenId(document.getCitizen().getId())
                .verifiedBy(document.getVerifiedBy())
                .verifiedDate(document.getVerifiedDate())
                .createdAt(document.getCreatedAt())
                .fileData(document.getFileData())
                .build();
    }


    public static CitizenDocument mapToEntity(CitizenDocumentRequestDto dto, Citizen citizen) throws IOException {
        return CitizenDocument.builder()
                .citizen(citizen)
                .documentType(dto.getDocumentType())
                .fileData(Base64.getEncoder().encodeToString(dto.getFile().getBytes()))
                .fileName(dto.getFile().getOriginalFilename())
                .uploadDate(LocalDate.now())
                .verifiedBy(dto.getVerifiedBy())
                .verifiedDate(dto.getVerifiedDate())
                .createdAt(LocalDate.now())
                //.updatedAt(LocalDate.now())

                .deathCertificateRequest(dto.getDeathCertificateRequestId())
                .build();
    }
}
