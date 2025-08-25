package id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.model.DeathCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
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
public interface DeathCertificateRepository extends JpaRepository<DeathCertificateRequest, Long> {
    boolean existsByDeceasedNameAndDateOfBirthAndDeceasedDateAndRequestedBy(
            String deceasedName,
            LocalDate dateOfBirth,
            LocalDate deceasedDate,
            Citizen requestedBy
    );

    List<DeathCertificateRequest> findByRequestedById(Long id);


    Optional<List<DeathCertificateRequest>> findByMunicipality(String municipality);
}
