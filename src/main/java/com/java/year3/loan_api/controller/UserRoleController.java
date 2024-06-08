package com.java.year3.loan_api.controller;

import com.java.year3.loan_api.dto.response.StatusResponseDTO;
import com.java.year3.loan_api.entity.UserRole;
import com.java.year3.loan_api.services.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class UserRoleController {
    private final UserRoleService userRoleService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserRole userRole) throws Exception {
        Map<String,Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        UserRole data = userRoleService.create(userRole);
        if(data != null) {
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("User Role created successfully");
            response.put("data", data);
        }else{
            statusResponseDTO.setErrorCode(204); // No Content
            statusResponseDTO.setErrorMessage("Your Content Unexceptionable!");
            response.put("data", null);
        }
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) throws Exception {
        Map<String,Object> response = new HashMap<>();
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        UserRole data = userRoleService.getById(id);
        if(data != null) {
            statusResponseDTO.setErrorCode(200);
            statusResponseDTO.setErrorMessage("User Role getById successfully");
            response.put("data", data);
        }else{
            statusResponseDTO.setErrorCode(204);
            statusResponseDTO.setErrorMessage("Your Content Unexceptionable!");
            response.put("data", null);
        }
        response.put("status", statusResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
