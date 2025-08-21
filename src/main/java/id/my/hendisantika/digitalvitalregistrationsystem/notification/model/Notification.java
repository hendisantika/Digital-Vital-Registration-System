package id.my.hendisantika.digitalvitalregistrationsystem.notification.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.certificateFile.CertificateFile;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.Citizen;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.enums.DeliveryChannel;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.enums.NotificationEvent;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.enums.NotificationStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.enums.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 22/08/25
 * Time: 05.23
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @JsonBackReference(value = "notification-data")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "citizen_id", nullable = true, referencedColumnName = "id")
    private Citizen citizen;

    @JsonBackReference(value = "certificate-notification")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "certificate_id", nullable = true, referencedColumnName = "id")
    private CertificateFile certificate;

    @Transient
    private Long certificateId;

    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @Enumerated(EnumType.STRING)
    private NotificationEvent event;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
    @Enumerated(EnumType.STRING)
    private DeliveryChannel channel;
    @Column(columnDefinition = "TEXT")
    private String message;
    private String email;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime sentAt;
    private Boolean readStatus = false;
}
