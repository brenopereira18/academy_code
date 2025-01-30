package com.AcademyCode.AcademyCode.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationDTO {

    @Schema(description = "Username do usuário", example = "joão_silva")
    private String username;
    private String password;
}
