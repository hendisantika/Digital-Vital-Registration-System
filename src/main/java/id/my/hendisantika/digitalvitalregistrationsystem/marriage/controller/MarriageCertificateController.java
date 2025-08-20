package id.my.hendisantika.digitalvitalregistrationsystem.marriage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.dto.MarriageCertificateResponseDto;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.model.MarriageCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.service.MarriageCertificateRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
@RequestMapping("api/v1/marriage")
public class MarriageCertificateController {
    private final MarriageCertificateRequestService marriageCertificateRequestService;
    private final ObjectMapper objectMapper;
    private final CertificateFileRepository certificateFileRepository;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MarriageCertificateRequest> saveMarriageCertificateRequest(
            @RequestPart("request") String requestJson,
            @RequestPart(value = "marriagePhoto", required = true) MultipartFile marriagePhoto,
            @RequestPart(value = "wardOfficeFile", required = true) MultipartFile wardOfficeFile
    ) throws JsonProcessingException {
        MarriageCertificateRequest request = objectMapper.readValue(requestJson, MarriageCertificateRequest.class);
        MarriageCertificateRequest save = marriageCertificateRequestService.save(request, marriagePhoto, wardOfficeFile);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MarriageCertificateResponseDto>> getAllMarriageCertificateRequests() {
        return new ResponseEntity<>(marriageCertificateRequestService.getAllRequest(), HttpStatus.OK);
    }
}
