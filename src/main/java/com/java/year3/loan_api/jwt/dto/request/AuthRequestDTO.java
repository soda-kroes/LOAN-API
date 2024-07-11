package com.java.year3.loan_api.jwt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestDTO {
    @NotNull(message = "The email field is required.")
    @Email(message = "The email address is invalid.")
    private String email;

    @NotNull(message = "The password field is required.")
    private String password;
}
