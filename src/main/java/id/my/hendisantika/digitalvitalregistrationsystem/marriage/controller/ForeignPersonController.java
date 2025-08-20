package id.my.hendisantika.digitalvitalregistrationsystem.marriage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.service.ForeignPersonService;
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
 * Date: 21/08/25
 * Time: 06.20
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/foreign-marriage")
public class ForeignPersonController {
    private final ForeignPersonService foreignPersonService;
    private final ObjectMapper objectMapper;
}
