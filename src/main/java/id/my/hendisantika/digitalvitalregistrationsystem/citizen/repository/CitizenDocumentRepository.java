package id.my.hendisantika.digitalvitalregistrationsystem.citizen.repository;

import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.CitizenDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 08.50
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface CitizenDocumentRepository extends JpaRepository<CitizenDocument, Long> {
    List<CitizenDocument> findByCitizen_Id(Long citizenId);
}
