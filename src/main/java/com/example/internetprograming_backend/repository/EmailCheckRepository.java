package com.example.internetprograming_backend.repository;

import com.example.internetprograming_backend.common.type.EmailCheckType;
import com.example.internetprograming_backend.domain.EmailCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface EmailCheckRepository extends JpaRepository<EmailCheck, Long> {

    Optional<EmailCheck> findTopByEmailAndEmailCheckTypeAndHasCheckIsTrueOrderByStartDateTimeDesc(String email, EmailCheckType emailCheckType);

    Optional<EmailCheck> findTopByAuthCodeAndEmailCheckTypeOrderByStartDateTimeDesc(String authCode, EmailCheckType emailCheckType);
}
