package id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.model.BirthCertificateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 06.21
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface BirthCertificateRepository extends JpaRepository<BirthCertificateRequest, Long> {
    boolean existsByChildNameAndDateOfBirthAndCitizen(String childName, LocalDate dateOfBirth, Citizen citizen);

    List<BirthCertificateRequest> findByCitizenId(Long id);

    List<BirthCertificateRequest> findByStaffUserMunicipality(String municipality);


    List<BirthCertificateRequest> findByMunicipality(String municipality);
}
