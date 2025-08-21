package id.my.hendisantika.digitalvitalregistrationsystem.notification.repository;

import id.my.hendisantika.digitalvitalregistrationsystem.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 22/08/25
 * Time: 05.24
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}