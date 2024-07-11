package com.java.year3.loan_api.jwt.dto.response;


import com.java.year3.loan_api.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDTO {
    private String errorCode;
    private String errorMessage;
    private String token;
    private UserInfo user;
}
