package id.my.hendisantika.digitalvitalregistrationsystem.marriage.mapper;

import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.Citizen;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.dto.MarriageCertificateResponseDto;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.model.MarriageCertificateRequest;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 21/08/25
 * Time: 06.10
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MarriageCertificateRequestMapper {

    public static MarriageCertificateResponseDto toMarriageCertificateResponseDto(MarriageCertificateRequest request) {
        String requestedByName = request.getRequestedBy() != null
                ? request.getRequestedBy().getFirstName() + " " + request.getRequestedBy().getLastName()
                : "N/A";

        String partnerName = "N/A";
        if (request.getForeignPartner() != null) {
            partnerName = request.getForeignPartner().getFullName();
        } else if (request.getHusband() != null && request.getWife() != null) {
            Citizen partner = request.getRequestedBy().getGender().name().equals("MALE")
                    ? request.getWife()
                    : request.getHusband();
            if (partner != null) {
                partnerName = partner.getFirstName() + " " + partner.getLastName();
            }
        }
        String partnerEmail = "N/A";
        if (request.getForeignPartner() != null) {
            partnerEmail = request.getForeignPartner().getEmail();
        } else if (request.getWife() != null && request.getHusband() != null) {
            Citizen partner = request.getRequestedBy().getGender().name().equals("MALE")
                    ? request.getWife()
                    : request.getHusband();
            if (partner != null) {
                partnerEmail = partner.getUserEmail();
            }
        }

        return MarriageCertificateResponseDto.builder()
                .id(request.getId())
                .requestedBy(requestedByName)
                .partnerName(partnerName)
                .marriageDate(request.getMarriageDate())
                .marriagePlace(request.getMunicipality())
                .marriageStatus(request.getStatus())
                .partnerEmail(partnerEmail)
                .scheduledTime(request.getScheduledTime())
                .build();
    }
}
