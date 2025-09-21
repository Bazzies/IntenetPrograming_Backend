package com.example.internetprograming_backend.repository;

import com.example.internetprograming_backend.domain.RejectionReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RejectionReasonRepository extends JpaRepository<RejectionReason, Long> {
}
