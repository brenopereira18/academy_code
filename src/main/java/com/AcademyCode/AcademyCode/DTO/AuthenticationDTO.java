package com.AcademyCode.AcademyCode.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationDTO {
    private String username;
    private String password;
}
