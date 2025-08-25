package id.my.hendisantika.digitalvitalregistrationsystem.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.Citizen;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Role;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.model.StaffUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 26/08/25
 * Time: 06.03
 * To change this template use File | Settings | File Templates.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users") // Optional: better naming in DB
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = true) // Nullable in OTP login, used later if password-based login is added
    private String password;

    @Column(length = 500)
    private String jwtToken;

    private Role role;

    @JsonBackReference(value = "user-data")
    @OneToOne(mappedBy = "user")
    private Citizen citizen;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "staffUser_id", referencedColumnName = "id")
    private StaffUser staffUser;
}
