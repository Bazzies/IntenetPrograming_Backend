package com.example.internetprograming_backend.repository;

import com.example.internetprograming_backend.domain.Member;
import com.example.internetprograming_backend.domain.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

    @Modifying
    @Query("""
                delete from MemberRole mr
                where mr.member in :member
            """)
    void deleteAllByMemberInBatch(@Param("member") Member member);
}
