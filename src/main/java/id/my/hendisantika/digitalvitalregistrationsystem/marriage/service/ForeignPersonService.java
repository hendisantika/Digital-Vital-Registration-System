package id.my.hendisantika.digitalvitalregistrationsystem.marriage.service;

import id.my.hendisantika.digitalvitalregistrationsystem.marriage.model.ForeignPerson;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.repository.ForeignPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 21/08/25
 * Time: 06.12
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class ForeignPersonService {
    private final ForeignPersonRepository foreignPersonRepository;

    public ForeignPerson save(ForeignPerson foreignPerson,
                              MultipartFile passportPhoto,
                              MultipartFile personPhoto,
                              MultipartFile personCitizenship
    ) {
        try {

            if (passportPhoto != null && !personPhoto.isEmpty()) {
                foreignPerson.setPassportFileData(Base64.getEncoder().encodeToString(passportPhoto.getBytes()));
                foreignPerson.setPassportFileName(passportPhoto.getOriginalFilename());

            }
            if (personPhoto != null && !personPhoto.isEmpty()) {
                foreignPerson.setPhotoFileData(Base64.getEncoder().encodeToString(personPhoto.getBytes()));
                foreignPerson.setPhotoFileName(personPhoto.getOriginalFilename());

            }

            if (personCitizenship != null && !personCitizenship.isEmpty()) {
                foreignPerson.setPersonCitizenshipFileData(Base64.getEncoder().encodeToString(personCitizenship.getBytes()));
                foreignPerson.setPersonCitizenshipFileName(personCitizenship.getOriginalFilename());
            }
            return foreignPersonRepository.save(foreignPerson);

        } catch (IOException e) {
            throw new RuntimeException("Error while saving foreign person", e);
        }
    }
}
