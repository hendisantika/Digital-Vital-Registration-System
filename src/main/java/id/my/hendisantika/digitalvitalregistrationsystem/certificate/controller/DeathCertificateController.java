package id.my.hendisantika.digitalvitalregistrationsystem.certificate.controller;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.certificateFile.CertificateFile;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.model.DeathCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.service.DeathCertificateRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 06.38
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/death-certificate")
public class DeathCertificateController {
    private final DeathCertificateRequestService deathCertificateRequestService;
    private final CertificateFileRepository certificateFileRepository;

    @PostMapping("/save")
    public ResponseEntity<DeathCertificateRequest> createDeathCertificateRequest(
            @RequestBody DeathCertificateRequest request) {
        DeathCertificateRequest savedRequest = deathCertificateRequestService.saveDeathCertificateRequest(request);
        return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
    }

    @PostMapping("/death/{id}/generate")
    public ResponseEntity<byte[]> generateDeathCertificate(@PathVariable long id) {
        CertificateFile file = certificateFileRepository.findByDeathCertificateRequestId(id)
                .orElseGet(() -> deathCertificateRequestService.generateDeathCertificateFile(id));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .contentType(MediaType.APPLICATION_PDF)
                .body(file.getFileData());
    }

    @GetMapping("/list")
    public ResponseEntity<List<DeathCertificateRequest>> getAllDeathCertificateRequests() {
        List<DeathCertificateRequest> list = deathCertificateRequestService.getAllRequests();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("{id}/list")
    public ResponseEntity<List<DeathCertificateRequest>> getAllDeathCertificateRequests(@PathVariable long id) {
        List<DeathCertificateRequest> list = deathCertificateRequestService.getAllRequestsByCitizenId(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PatchMapping("{id}/approve")
    public ResponseEntity<Map<String, String>> approveDeathCertificate(@PathVariable long id) {
        deathCertificateRequestService.approveDeathCertificateRequest(id);
        return ResponseEntity.ok(Map.of("status", "approved"));
    }

    @PatchMapping("{id}/reject")
    public ResponseEntity<Map<String, String>> updateDeathCertificateRequest(@PathVariable long id) {
        deathCertificateRequestService.rejectDeathCertificateRequest(id);
        return ResponseEntity.ok(Map.of("status", "rejected"));
    }

    @GetMapping("/count-death")
    public ResponseEntity<Long> countDeathCertificateRequests() {
        return ResponseEntity.ok().body(deathCertificateRequestService.countDeathCertificateRequest());
    }

    @GetMapping("/by-municipality")
    public ResponseEntity<Optional<List<DeathCertificateRequest>>> getDeathCertificateRequestsByMunicipality(@RequestParam String municipality) {
        return ResponseEntity.ok().body(deathCertificateRequestService.getDeathCertificateRequestByMunicipality(municipality));
    }

    @PatchMapping("{id}/verify")
    public ResponseEntity<Map<String, String>> verifyDeathCertificate(@PathVariable long id) {
        deathCertificateRequestService.approveByVerifier(id);
        return ResponseEntity.ok(Map.of("status", "verified"));
    }
}
