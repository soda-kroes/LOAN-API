package com.java.year3.loan_api.services;

import com.java.year3.loan_api.dto.response.StatusResponseDTO;
import com.java.year3.loan_api.dto.response.UserResponseDTO;
import com.java.year3.loan_api.entity.UserInfo;
import com.java.year3.loan_api.exception.NotFoundException;
import com.java.year3.loan_api.jwt.dto.request.UserRegisterRequestDTO;

import java.util.List;

public interface UserService {
    List<UserInfo> getList();
    UserInfo getById(Long id) throws NotFoundException;
    Boolean deleteById(Long id) throws NotFoundException;
    UserResponseDTO updateUser(Long id, UserRegisterRequestDTO requestDTO) throws NotFoundException;

    StatusResponseDTO disableUserById(Long id) throws  NotFoundException;
    StatusResponseDTO enableUserById(Long id) throws NotFoundException;
    StatusResponseDTO changeDefaultPassword(Long id, String newPassword) throws NotFoundException;
    StatusResponseDTO resetPassword(Long id, String password) throws NotFoundException;

}
