package com.java.year3.loan_api.services;

import com.java.year3.loan_api.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    UserRole create(UserRole userRoleRequest) throws Exception;
    UserRole getById(Long id) throws Exception;
    List<UserRole> getAll();
}
