package com.java.year3.loan_api.repository;

import com.java.year3.loan_api.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    boolean existsByName(String name);
}
