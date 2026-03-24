package com.bookmyshow.bmscore.requestDTO;

import lombok.Data;

@Data
public class SaveUserRequestDTO {
    private String fullName;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
