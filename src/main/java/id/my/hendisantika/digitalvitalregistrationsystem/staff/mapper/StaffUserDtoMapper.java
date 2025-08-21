package id.my.hendisantika.digitalvitalregistrationsystem.staff.mapper;

import id.my.hendisantika.digitalvitalregistrationsystem.staff.dto.StaffUserResponseDto;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.model.StaffUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 22/08/25
 * Time: 05.43
 * To change this template use File | Settings | File Templates.
 */
@Component
@RequiredArgsConstructor
public class StaffUserDtoMapper {
    public static StaffUserResponseDto staffUserResponseDto(StaffUser staffUser) {
        return StaffUserResponseDto.builder()
                .id(staffUser.getId())
                .fullName(staffUser.getFullName())
                .email(staffUser.getEmail())
                .role(staffUser.getRole())
                .status(staffUser.getStatus())
                .department(staffUser.getDepartment())
                .designation(staffUser.getDesignation())
                .phoneNumber(staffUser.getPhoneNumber())
                .createdAt(staffUser.getCreatedAt())
                .addedBy(staffUser.getAddedBy())
                .municipality(staffUser.getMunicipality())
                .district(staffUser.getDistrict())
                .build();
    }
}
