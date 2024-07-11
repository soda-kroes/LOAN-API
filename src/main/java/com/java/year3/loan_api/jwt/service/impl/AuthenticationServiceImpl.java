package com.java.year3.loan_api.jwt.service.impl;

import com.java.year3.loan_api.entity.UserInfo;
import com.java.year3.loan_api.jwt.dto.request.AuthRequestDTO;
import com.java.year3.loan_api.jwt.dto.request.UserRegisterRequestDTO;
import com.java.year3.loan_api.jwt.dto.response.AuthResponseDTO;
import com.java.year3.loan_api.jwt.service.AuthenticationService;
import com.java.year3.loan_api.jwt.service.JwtService;
import com.java.year3.loan_api.repository.UserInfoRepository;
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
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserInfoRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRoleService userRoleService;

    public AuthResponseDTO register(UserRegisterRequestDTO request) throws Exception {
        boolean isUserEmail = userRepository.existsByEmail(request.getEmail());
        if (isUserEmail) {
            return AuthResponseDTO.builder().errorCode(ApiResponseUtils.USER_EXISTS_CODE).errorMessage(ApiResponseUtils.USER_EXISTS_MESSAGE).build();
        }

        UserInfo user = UserInfo.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .gender(request.getGender())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(1).createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .userRoles(Collections.singleton(userRoleService.getById(request.getUserRoleId()))).build();

        userRepository.save(user);

        return AuthResponseDTO.builder().errorCode(ApiResponseUtils.SUCCESS_CODE).errorMessage(ApiResponseUtils.SUCCESS_MESSAGE).token(null).build();
    }

    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        try {
            UserInfo user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found."));
            var jwtToken = jwtService.generateToken(user);

            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            } catch (AuthenticationException e) {
                return AuthResponseDTO.builder()
                        .errorCode("999")
                        .errorMessage("Invalid credentials.")
                        .token(null)
                        .build();
            }

            if (user.getStatus() != 1) {
                return AuthResponseDTO.builder()
                        .errorCode("202")
                        .errorMessage("User is inactive. Please contact the admin.")
                        .token(null)
                        .build();
            }

            if (user.getChangePassword() == 0) {
                return AuthResponseDTO.builder()
                        .errorCode("203")
                        .errorMessage("Please change the default password.")
                        .user(user)
                        .token(jwtToken)
                        .build();
            }

            return AuthResponseDTO.builder()
                    .errorCode(ApiResponseUtils.SUCCESS_CODE)
                    .errorMessage(ApiResponseUtils.SUCCESS_MESSAGE)
                    .token(jwtToken)
                    .user(user)
                    .build();
        } catch (UsernameNotFoundException e) {
            return AuthResponseDTO.builder()
                    .errorCode("404")
                    .errorMessage("User not found.")
                    .token(null)
                    .build();
        }
    }

}
