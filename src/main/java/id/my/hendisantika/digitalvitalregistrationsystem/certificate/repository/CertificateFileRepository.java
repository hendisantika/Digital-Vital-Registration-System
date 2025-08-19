package id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.certificateFile.CertificateFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 06.22
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface CertificateFileRepository extends JpaRepository<CertificateFile, Long> {
    CertificateFile findByReferenceNumber(String reference);

    Optional<CertificateFile> findByBirthCertificateRequestId(Long requestId);

    Optional<CertificateFile> findByCitizen_Id(Long id);

    Optional<CertificateFile> findByDeathCertificateRequestId(long id);

    Optional<CertificateFile> findByMarriageCertificateRequestId(long id);
}
