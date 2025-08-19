package id.my.hendisantika.digitalvitalregistrationsystem.certificate.service;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.certificateFile.CertificateFile;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.enums.CertificateStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.enums.CertificateType;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.model.BirthCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.aspectj.weaver.SignatureUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.Map;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 06.23
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class BirthCertificateReportService {
    private final CertificateFileRepository certificateFileRepository;
    private final SignatureKeysService signatureKeysService;

    @Transactional
    public CertificateFile generateBirthCertificateReport(Map<String, Object> parameters, Citizen citizen, BirthCertificateRequest request) {
        try {
            String referenceNumber = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            parameters.put("referenceNumber", referenceNumber);
            parameters.put("signedMark", "This document bears a government-authorized digital signature, ensuring its authenticity and integrity.");
            parameters.put("tamperWarning", "Any modification to this document will be detected and deemed unauthorized. Tampering with official documents is a punishable offense under applicable laws.");

            InputStream reportStream = new ClassPathResource("/reports/birthCertificateReport.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            BigInteger signature = SignatureUtils.sign(pdfBytes, signatureKeysService.getPrivateKey(), signatureKeysService.getModulus());

            CertificateFile file = CertificateFile.builder()
                    .filePath("birth_certificate_" + request.getId() + ".pdf")
                    .birthCertificateRequest(request)
                    .citizen(citizen)
                    .fileData(pdfBytes)
                    .digitalSignature(signature.toString())
                    .publicKey(signatureKeysService.getPublicKey().toString())
                    .modulus(signatureKeysService.getModulus().toString())
                    .status(CertificateStatus.APPROVED)
                    .certificateType(CertificateType.BIRTH)
                    .referenceNumber(referenceNumber)
                    .build();

            return certificateFileRepository.save(file);
        } catch (Exception e) {
            throw new RuntimeException("Error generating report", e);
        }
    }
}
