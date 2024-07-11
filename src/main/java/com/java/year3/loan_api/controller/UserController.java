package com.java.year3.loan_api.controller;


import com.java.year3.loan_api.dto.response.StatusResponseDTO;
import com.java.year3.loan_api.dto.response.UserResponseDTO;
import com.java.year3.loan_api.entity.UserInfo;
import com.java.year3.loan_api.exception.NotFoundException;
import com.java.year3.loan_api.jwt.dto.request.UserRegisterRequestDTO;
import com.java.year3.loan_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<Map<String, Object>> getAllUser() {
        Map<String, Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        List<UserInfo> data = userService.getList();
        if (data.isEmpty()) {
            statusResponseDTO.setErrorCode(HttpStatus.NO_CONTENT.value());
            statusResponseDTO.setErrorMessage("User List is empty");
            response.put("data", null);
        } else {
            statusResponseDTO.setErrorCode(HttpStatus.OK.value());
            statusResponseDTO.setErrorMessage("User list retrieved successfully");
            response.put("data", data);
        }
        response.put("status", statusResponseDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getOne(@PathVariable(name = "id") Long id) throws NotFoundException {
        Map<String, Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        try {
            UserInfo data = userService.getById(id);
            statusResponseDTO.setErrorCode(HttpStatus.OK.value());
            statusResponseDTO.setErrorMessage("User retrieved successfully");
            response.put("data", data);
        } catch (NotFoundException ex) {
            statusResponseDTO.setErrorCode(HttpStatus.NOT_FOUND.value());
            statusResponseDTO.setErrorMessage(ex.getMessage());
            response.put("data", null);
        }
        response.put("status", statusResponseDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StatusResponseDTO> deleteUser(@PathVariable(name = "id") Long id) {
        StatusResponseDTO response = new StatusResponseDTO();
        try {
            boolean isDeleted = userService.deleteById(id);
            if (isDeleted) {
                response.setErrorCode(200);
                response.setErrorMessage("User deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.setErrorCode(HttpStatus.NOT_FOUND.value());
                response.setErrorMessage("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception ex) {
            response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setErrorMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") Long id, @RequestBody UserRegisterRequestDTO requestDTO) throws NotFoundException {
        UserResponseDTO response = userService.updateUser(id, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/enable/{id}")
    public ResponseEntity<?> enableUser(@PathVariable(name = "id") Long id) throws NotFoundException {
        StatusResponseDTO statusResponseDTO = userService.enableUserById(id);
        return new ResponseEntity<>(statusResponseDTO,HttpStatus.OK);
    }

    @GetMapping("/disable/{id}")
    public ResponseEntity<?> disableUser(@PathVariable(name = "id") Long id) throws NotFoundException {
        StatusResponseDTO statusResponseDTO = userService.disableUserById(id);
        return new ResponseEntity<>(statusResponseDTO,HttpStatus.OK);
    }

    @PostMapping("/change-default-pass")
    public ResponseEntity<StatusResponseDTO> changeDefaultPassword(
            @RequestParam("id") Long id,
            @RequestParam("password") String password) throws NotFoundException {
        StatusResponseDTO statusResponseDTO = userService.changeDefaultPassword(id, password);
        return ResponseEntity.ok(statusResponseDTO);
    }

    @PostMapping("/reset-pass")
    public ResponseEntity<StatusResponseDTO> retsetPassword(
            @RequestParam("id") Long id,
            @RequestParam("password") String password) throws NotFoundException {
        StatusResponseDTO statusResponseDTO = userService.resetPassword(id, password);
        return ResponseEntity.ok(statusResponseDTO);
    }

}
