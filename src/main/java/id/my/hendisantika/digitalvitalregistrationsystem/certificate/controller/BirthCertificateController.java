package id.my.hendisantika.digitalvitalregistrationsystem.certificate.controller;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.certificateFile.CertificateFile;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.model.BirthCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.service.BirthCertificateReportService;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.service.BirthCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 06.34
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/certificate")
public class BirthCertificateController {
    private final BirthCertificateService birthCertificateService;
    private final BirthCertificateReportService birthCertificateReportService;
    private final CertificateFileRepository certificateFileRepository;

    @PostMapping("/save")
    public ResponseEntity<BirthCertificateRequest> saveCertificate(@RequestBody BirthCertificateRequest birthCertificate) {
        BirthCertificateRequest savedCertificate = birthCertificateService.saveCertificate(birthCertificate);
        return ResponseEntity.ok(savedCertificate);
    }

    @GetMapping("/birth/{id}/generate")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long id) {
        CertificateFile file = certificateFileRepository.findByBirthCertificateRequestId(id)
                .orElseGet(() -> birthCertificateService.generateBirthCertificateReport(id));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilePath())
                .contentType(MediaType.APPLICATION_PDF)
                .body(file.getFileData());
    }

    @GetMapping("/download/{referenceNumber}")
    public ResponseEntity<?> downloadCertificateByReferenceNumber(@PathVariable String referenceNumber) {
        if (referenceNumber == null || referenceNumber.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Reference number must not be empty.");
        }

        CertificateFile certificateFile = certificateFileRepository.findByReferenceNumber(referenceNumber);
        if (certificateFile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Certificate not found for the given reference number.");
        }

        byte[] pdf = certificateFile.getFileData();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + certificateFile.getFilePath())
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
