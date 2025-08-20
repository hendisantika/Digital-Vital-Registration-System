package id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums;

import lombok.Getter;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 08.51
 * To change this template use File | Settings | File Templates.
 */
@Getter
public enum DocumentType {
    CITIZENSHIP("Citizenship Card"),
    VOTER_ID("Voter ID Card"),
    NATIONAL_ID("National ID"),
    LICENCE("License"),
    DEATH("Death"),
    BIRTH("Birth"),
    MARRIAGE("Marriage"),
    PASSPORT("Passport");

    private final String label;

    DocumentType(String label) {
        this.label = label;
    }
}
