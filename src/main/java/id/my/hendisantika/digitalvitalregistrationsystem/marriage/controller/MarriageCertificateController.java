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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

    @GetMapping("{id}/by-id")
    private ResponseEntity<MarriageCertificateResponseDto> getMarriageCertificateRequestById(@PathVariable Long id) {
        return new ResponseEntity<>(marriageCertificateRequestService.getById(id), HttpStatus.OK);
    }

    @GetMapping("exist")
    private ResponseEntity<Boolean> existsByRequestedId(@RequestParam("requestedBy") Long id) {
        return new ResponseEntity<>(marriageCertificateRequestService.existsByRequestId(id), HttpStatus.OK);
    }

    @PatchMapping("/send-video-link/{id}")
    private ResponseEntity<Map<String, String>> verifyByVideoCall(@PathVariable("id") Long id) {
        marriageCertificateRequestService.sendVideoVerificationLink(id);
        return ResponseEntity.ok().body(Map.of("message", "verification link success"));
    }

    @PatchMapping("/{id}/approve")
    private ResponseEntity<Map<String, String>> approve(@PathVariable("id") Long id) {
        marriageCertificateRequestService.approve(id);
        generateMarriageCertificate(id);
        return ResponseEntity.ok().body(Map.of("message", "Approved success"));
    }

    @PatchMapping("/{id}/reject")
    private ResponseEntity<Map<String, String>> reject(@PathVariable("id") Long id) {
        marriageCertificateRequestService.reject(id);
        return ResponseEntity.ok().body(Map.of("message", "Rejected success"));
    }

    @GetMapping("/count-marriage")
    public ResponseEntity<Long> countMarriageCertificateRequests() {
        return new ResponseEntity<>(marriageCertificateRequestService.countMarriageCertificateRequests(), HttpStatus.OK);
    }

    @GetMapping("/by-request/{id}")
    public ResponseEntity<MarriageCertificateRequest> getMarriageCertificateById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(marriageCertificateRequestService.getRequestById(id), HttpStatus.OK);
    }
}
