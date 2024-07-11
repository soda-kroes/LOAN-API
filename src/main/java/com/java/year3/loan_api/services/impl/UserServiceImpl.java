package com.java.year3.loan_api.services.impl;

import com.java.year3.loan_api.dto.response.StatusResponseDTO;
import com.java.year3.loan_api.dto.response.UserResponseDTO;
import com.java.year3.loan_api.entity.UserInfo;
import com.java.year3.loan_api.entity.UserRole;
import com.java.year3.loan_api.exception.NotFoundException;
import com.java.year3.loan_api.jwt.dto.request.UserRegisterRequestDTO;
import com.java.year3.loan_api.repository.UserInfoRepository;
import com.java.year3.loan_api.repository.UserRoleRepository;
import com.java.year3.loan_api.services.UserService;
import com.java.year3.loan_api.utils.ApiResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserInfoRepository userInfoRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public List<UserInfo> getList() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return userInfoRepository.findAll(sort);
    }


    @Override
    public UserInfo getById(Long id) throws NotFoundException {
        return userInfoRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id = " + id + " not found."));
    }


    @Override
    public Boolean deleteById(Long id) throws NotFoundException {
        boolean isDelete = false;
        UserInfo userInfo = userInfoRepository.findById(id).orElseThrow(() -> new NotFoundException("No loan found with id " + id));

        if (Objects.equals(userInfo.getId(), id)) {
            // Delete the user_roles associated with the user_info
            Set<UserRole> userRoles = userInfo.getUserRoles();
            if (!userRoles.isEmpty()) {
                userInfo.getUserRoles().clear(); // Remove all user_roles from the user_info entity
                userInfoRepository.save(userInfo); // Update the user_info entity to remove the associations
            }

            // Delete the user_info entity
            userInfoRepository.delete(userInfo);
            isDelete = true;
        }
        return isDelete;
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRegisterRequestDTO requestDTO) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        try {
            Optional<UserInfo> optionalUserInfo = userInfoRepository.findById(id);
            if (optionalUserInfo.isPresent()) {
                UserInfo userInfo = optionalUserInfo.get();
                userInfo.setEmail(requestDTO.getEmail());
                userInfo.setFirstname(requestDTO.getFirstname());
                userInfo.setLastname(requestDTO.getLastname());

                Optional<UserRole> optionalUserRole = userRoleRepository.findById(requestDTO.getUserRoleId());
                if (optionalUserRole.isPresent()) {
                    UserRole userRole = optionalUserRole.get();
                    userInfo.getUserRoles().clear();
                    userInfo.getUserRoles().add(userRole);
                    userInfoRepository.save(userInfo);

                    responseDTO.setErrorCode(HttpStatus.OK.value());
                    responseDTO.setErrorMessage(ApiResponseUtils.SUCCESS_MESSAGE);
                } else {
                    responseDTO.setErrorCode(HttpStatus.NOT_FOUND.value());
                    responseDTO.setErrorMessage("User role not found with id: " + requestDTO.getUserRoleId());
                }
            } else {
                responseDTO.setErrorCode(HttpStatus.NOT_FOUND.value());
                responseDTO.setErrorMessage("User not found with id: " + id);
            }
        } catch (DataAccessException ex) {
            responseDTO.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorMessage(ex.getMessage());
        }

        return responseDTO;
    }

    @Override
    @Transactional
    public StatusResponseDTO disableUserById(Long id) throws NotFoundException {
        UserInfo userInfo = userInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No user found with id " + id));

        userInfoRepository.disableUserById(id);

        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        statusResponseDTO.setErrorCode(200);
        statusResponseDTO.setErrorMessage("User with id " + id + " has been disabled.");
        // Set other properties in the status response if needed
        return statusResponseDTO;
    }

    @Override
    @Transactional
    public StatusResponseDTO enableUserById(Long id) throws NotFoundException {
        UserInfo userInfo = userInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No user found with id " + id));

        userInfoRepository.enableUserById(id);

        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        statusResponseDTO.setErrorCode(200);
        statusResponseDTO.setErrorMessage("User with id " + id + " has been enabled.");
        // Set other properties in the status response if needed
        return statusResponseDTO;
    }

    @Override
    public StatusResponseDTO changeDefaultPassword(Long id, String newPassword) throws NotFoundException {
        UserInfo userInfo = userInfoRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found with user Id = " + id));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        userInfo.setPassword(encodedPassword);
        userInfo.setChangePassword(1);
        userInfo.setModifiedDate(LocalDateTime.now());
        userInfoRepository.save(userInfo);
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        statusResponseDTO.setErrorCode(200);
        statusResponseDTO.setErrorMessage("User with id " + id + " has been changed.");
        return statusResponseDTO;
    }

    @Override
    public StatusResponseDTO resetPassword(Long id, String password) throws NotFoundException {
        UserInfo userInfo = userInfoRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found with user Id = " + id));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        userInfo.setPassword(encodedPassword);
        userInfo.setChangePassword(0);
        userInfo.setModifiedDate(LocalDateTime.now());
        userInfoRepository.save(userInfo);
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        statusResponseDTO.setErrorCode(200);
        statusResponseDTO.setErrorMessage("User with id " + id + " has been reset.");
        return statusResponseDTO;
    }


}
