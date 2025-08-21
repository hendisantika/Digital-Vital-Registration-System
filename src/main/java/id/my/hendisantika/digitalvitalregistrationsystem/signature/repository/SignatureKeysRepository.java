package id.my.hendisantika.digitalvitalregistrationsystem.signature.repository;

import id.my.hendisantika.digitalvitalregistrationsystem.signature.model.SignatureKeys;
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
 * Date: 22/08/25
 * Time: 05.29
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface SignatureKeysRepository extends JpaRepository<SignatureKeys, Long> {
    Optional<SignatureKeys> findTopByOrderByIdDesc();
}
