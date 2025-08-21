package id.my.hendisantika.digitalvitalregistrationsystem.staff.controller;

import id.my.hendisantika.digitalvitalregistrationsystem.staff.service.UserStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
