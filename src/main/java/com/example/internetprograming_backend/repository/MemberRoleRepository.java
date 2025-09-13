package com.example.internetprograming_backend.repository;

import com.example.internetprograming_backend.domain.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

}
