package id.my.hendisantika.digitalvitalregistrationsystem.citizen.mapper;

import id.my.hendisantika.digitalvitalregistrationsystem.citizen.dto.CitizenDocumentResponseDto;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.CitizenDocument;

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

}
