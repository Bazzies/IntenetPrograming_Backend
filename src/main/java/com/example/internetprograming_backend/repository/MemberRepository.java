package com.example.internetprograming_backend.repository;

import com.example.internetprograming_backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByEmailAndWithdrawIsFalse(String email);

    boolean existsMemberByEmail(String email);

}
