package com.java.year3.loan_api.jwt.service;


import com.java.year3.loan_api.entity.UserInfo;
import com.java.year3.loan_api.jwt.dto.request.AuthRequestDTO;
import com.java.year3.loan_api.jwt.dto.request.UserRegisterRequestDTO;
import com.java.year3.loan_api.jwt.dto.response.AuthResponseDTO;
import com.java.year3.loan_api.jwt.repository.UserInfoRepository;
import com.java.year3.loan_api.services.UserRoleService;
import com.java.year3.loan_api.utils.ApiResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserInfoRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRoleService userRoleService;


    public AuthResponseDTO register(UserRegisterRequestDTO request) throws Exception {
        boolean isUserEmail = userRepository.existsByEmail(request.getEmail());
        if (isUserEmail) {
            return AuthResponseDTO.builder().errorCode(ApiResponseUtils.USER_EXISTS_CODE).errorMessage(ApiResponseUtils.USER_EXISTS_MESSAGE).token(null).build();
        }
        UserInfo user = UserInfo.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdDate(LocalDateTime.now())
                .roles(Collections.singleton(userRoleService.getById(request.getUserRoleId()))).build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthResponseDTO.builder()
                .errorCode(ApiResponseUtils.SUCCESS_CODE)
                .errorMessage(ApiResponseUtils.SUCCESS_MESSAGE)
                .token(jwtToken)
                .build();
    }


    public AuthResponseDTO authenticate(AuthRequestDTO request) throws AccessDeniedException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            return AuthResponseDTO.builder()
                    .errorCode(ApiResponseUtils.FORBIDDEN_CODE)
                    .errorMessage(ApiResponseUtils.FORBIDDEN_MESSAGE).build();
        }

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found."));

        var jwtToken = jwtService.generateToken(user);
        return AuthResponseDTO.builder().errorCode(ApiResponseUtils.SUCCESS_CODE).errorMessage(ApiResponseUtils.SUCCESS_MESSAGE).token(jwtToken).build();
    }
}
