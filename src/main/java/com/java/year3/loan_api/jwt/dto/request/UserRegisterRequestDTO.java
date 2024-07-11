package com.java.year3.loan_api.jwt.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserRegisterRequestDTO {
    @NotNull(message = "The firstname field is required.")
    @JsonProperty("firstname")
    private String firstname;

    @NotNull(message = "The lastname field is required.")
    @JsonProperty("lastname")
    private String lastname;

    @NotNull(message = "The gender field is required.")
    @JsonProperty("gender")
    private String gender;

    @NotNull(message = "The email field is required.")
    @Email(message = "The email address is invalid.")
    @JsonProperty("email")
    private String email;

    @NotNull(message = "The password field is required.")
    @JsonProperty("password")
    private String password;

    @JsonProperty("created_date")
    private String createdDate;

    @NotNull(message = "The user role ID field is required.")
    @JsonProperty("user_role_id")
    private Long userRoleId;
}
