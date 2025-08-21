package id.my.hendisantika.digitalvitalregistrationsystem.staff.controller;

import id.my.hendisantika.digitalvitalregistrationsystem.staff.dto.StaffUserRequestDto;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.dto.StaffUserResponseDto;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Role;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.model.StaffUser;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.service.UserStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 22/08/25
 * Time: 05.48
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
public class StaffUserController {

    private final UserStaffService userStaffService;

    @PostMapping("/super-admin")
    public ResponseEntity<StaffUserResponseDto> createSuperAdmin(@RequestBody StaffUserRequestDto dto) {
        StaffUserResponseDto response = userStaffService.createSuperAdmin(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin")
    public ResponseEntity<StaffUserResponseDto> createStaff(@RequestBody StaffUserRequestDto dto, @AuthenticationPrincipal StaffUser staffUser) {
        StaffUserResponseDto response = userStaffService.createStaff(dto, staffUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-role")
    public ResponseEntity<List<StaffUserResponseDto>> getStaffByRole(@RequestParam("role") Role role) {
        return ResponseEntity.ok(userStaffService.getStaffByRole(role));
    }
}
