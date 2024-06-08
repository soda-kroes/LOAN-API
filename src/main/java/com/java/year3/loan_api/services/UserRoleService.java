package com.java.year3.loan_api.services;

import com.java.year3.loan_api.entity.UserRole;
import com.java.year3.loan_api.exception.AlreadyExistException;

public interface UserRoleService {
    UserRole create(UserRole userRoleRequest) throws Exception;
    UserRole getById(Long id) throws Exception;
}
