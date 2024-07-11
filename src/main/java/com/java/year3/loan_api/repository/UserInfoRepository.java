package com.java.year3.loan_api.repository;

import com.java.year3.loan_api.dto.response.StatusResponseDTO;
import com.java.year3.loan_api.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByEmail(String email);
    Boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE UserInfo u SET u.status = 0 WHERE u.id = :id")
    void disableUserById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE UserInfo u SET u.status = 1 WHERE u.id = :id")
    void enableUserById(@Param("id") Long id);

}
