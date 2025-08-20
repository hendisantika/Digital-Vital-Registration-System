package id.my.hendisantika.digitalvitalregistrationsystem.citizen.mapper;

import id.my.hendisantika.digitalvitalregistrationsystem.citizen.dto.CitizenResponseDto;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.Citizen;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 08.57
 * To change this template use File | Settings | File Templates.
 */
public class CitizenDtoMapper {
    public static CitizenResponseDto mapToDto(Citizen citizen) {
        return CitizenResponseDto.builder()
                .id(citizen.getId())
                .firstName(citizen.getFirstName())
                .middleName(citizen.getMiddleName())
                .lastName(citizen.getLastName())
                .dateOfBirth(citizen.getDateOfBirth())
                .phoneNo(citizen.getPhoneNo())
                .fatherName(citizen.getFatherName())
                .motherName(citizen.getMotherName())
                .grandfatherName(citizen.getGrandfatherName())
                .grandmotherName(citizen.getGrandmotherName())
                .nationality(citizen.getNationality())
                .district(citizen.getDistrict())
                .municipality(citizen.getMunicipality())
                .wardNo(citizen.getWardNo())
                .gender(citizen.getGender())
                .tole(citizen.getTole())
                .spouseName(citizen.getSpouseName())
                .verifiedBy(citizen.getVerifiedBy())
                .verifiedDate(citizen.getVerifiedDate())
                .createdAt(citizen.getCreatedAt())
                .reasonForRejection(citizen.getReasonForRejection())
                .status(citizen.getStatus())
                .marriageStatus(citizen.getMarriageStatus())
                .citizenshipNumber(citizen.getCitizenshipNumber())
                .updatedAt(citizen.getUpdatedAt())
                .build();
    }
}
