package id.my.hendisantika.digitalvitalregistrationsystem.marriage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.model.ForeignPerson;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.service.ForeignPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/save")
    public ResponseEntity<ForeignPerson> save(@RequestPart("foreignPerson") String foreignPersonJson,
                                              @RequestPart(required = true) MultipartFile passportPhoto,
                                              @RequestPart(required = true) MultipartFile personPhoto,
                                              @RequestPart(required = true) MultipartFile personCitizenship) throws JsonProcessingException {
        ForeignPerson foreignPerson = objectMapper.readValue(foreignPersonJson, ForeignPerson.class);

        ForeignPerson save = foreignPersonService.save(foreignPerson, passportPhoto, personPhoto, personCitizenship);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }
}
