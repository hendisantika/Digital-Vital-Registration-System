package id.my.hendisantika.digitalvitalregistrationsystem.certificate.enums;

import lombok.Getter;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 06.19
 * To change this template use File | Settings | File Templates.
 */
@Getter
public enum CertificateStatus {
    PENDING("Pending"),
    PENDING_VIDEO_CALL_VERIFICATION("Pending Video Call Verification"),
    APPROVED_BY_VERIFIER("Approved by Verifier"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String label;

    CertificateStatus(String label) {
        this.label = label;
    }
}
