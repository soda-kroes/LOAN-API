package com.java.year3.loan_api.jwt.repository;

import com.java.year3.loan_api.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
    Optional<UserInfo> findByEmail(String email);
    Boolean existsByEmail(String email);
}
