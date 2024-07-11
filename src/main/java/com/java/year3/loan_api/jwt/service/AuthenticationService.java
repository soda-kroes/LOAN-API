package com.java.year3.loan_api.jwt.service;


import com.java.year3.loan_api.dto.response.UserResponseDTO;
import com.java.year3.loan_api.entity.UserInfo;
import com.java.year3.loan_api.exception.NotFoundException;
import com.java.year3.loan_api.jwt.dto.request.AuthRequestDTO;
import com.java.year3.loan_api.jwt.dto.request.UserRegisterRequestDTO;
import com.java.year3.loan_api.jwt.dto.response.AuthResponseDTO;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface AuthenticationService {

    AuthResponseDTO register(UserRegisterRequestDTO request) throws Exception;

    AuthResponseDTO authenticate(AuthRequestDTO request) throws AccessDeniedException;


}