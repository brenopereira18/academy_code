package com.academycode.academycode.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserProfileDTO {

    @Schema(description = "Nome do usuário", example = "João")
    private String name;
    private String password;
}
