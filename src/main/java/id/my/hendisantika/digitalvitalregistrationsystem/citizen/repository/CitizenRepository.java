package id.my.hendisantika.digitalvitalregistrationsystem.citizen.repository;

import id.my.hendisantika.digitalvitalregistrationsystem.citizen.dto.CitizenResponseDto;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.Gender;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
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
 * Time: 08.50
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    @Query("SELECT COUNT(c) FROM Citizen c WHERE c.gender = :gender")
    Long countByGender(@Param("gender") Gender gender);

    Optional<Citizen> findByUserEmail(String email);

    Optional<Citizen> findByUser(User user);

    Citizen findCitizenByUserId(Long userId);

    Citizen findByCitizenshipNumberAndDateOfBirth(String citizenshipNumber, LocalDate dateOfBirth);

    List<CitizenResponseDto> findByMunicipality(String municipality);
}
