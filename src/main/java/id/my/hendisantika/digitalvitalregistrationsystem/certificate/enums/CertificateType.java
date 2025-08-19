package id.my.hendisantika.digitalvitalregistrationsystem.certificate.enums;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 06.20
 * To change this template use File | Settings | File Templates.
 */
public enum CertificateType {
    BIRTH("Birth"),
    DEATH("Death"),
    MARRIAGE("Marriage");

    private final String label;

    CertificateType(String label) {
        this.label = label;
    }
}
