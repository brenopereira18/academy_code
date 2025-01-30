package com.AcademyCode.AcademyCode.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileDTO {

    @Schema(description = "Nome do usuário", example = "João")
    private String name;
    private String password;
}
