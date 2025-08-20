package id.my.hendisantika.digitalvitalregistrationsystem.marriage.repository;

import id.my.hendisantika.digitalvitalregistrationsystem.marriage.model.ForeignPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
public interface ForeignPersonRepository extends JpaRepository<ForeignPerson, Long> {
}
