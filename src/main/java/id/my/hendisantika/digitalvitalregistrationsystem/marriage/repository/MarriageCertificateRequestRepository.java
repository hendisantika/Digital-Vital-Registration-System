package id.my.hendisantika.digitalvitalregistrationsystem.marriage.repository;

import id.my.hendisantika.digitalvitalregistrationsystem.marriage.model.MarriageCertificateRequest;
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
 * Date: 21/08/25
 * Time: 06.11
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface MarriageCertificateRequestRepository extends JpaRepository<MarriageCertificateRequest, Long> {
    boolean existsByRequestedById(Long id);

    Optional<MarriageCertificateRequest> findByRequestedById(Long id);


    Optional<MarriageCertificateRequest> findByMunicipality(String municipality);
}
