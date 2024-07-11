package com.java.year3.loan_api.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfo {
    private Long id;
    private String firstname;
    private String lastname;
    private String gender;
    private String email;
    private String password;
    private int status;
    private int changePassword;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int user_role_id;
}
