package id.my.hendisantika.digitalvitalregistrationsystem.citizen.service;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.repository.CitizenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 08.58
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CitizenService {
    private final JwtUtil jwtUtil;

    private final CitizenRepository citizenRepository;
    private final NotificationService notificationService;
    private final CertificateFileRepository certificateFileRepository;


    //  @CacheEvict(value = {"citizenCount", "citizenList","citizenById"}, allEntries = true)
    // @CachePut(value = "citizen", key = "#result.id")
}
