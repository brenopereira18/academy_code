package com.academycode.academycode.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthenticationUserDTO {

    @Schema(description = "Username do usuário", example = "joão_silva")
    private String username;
    private String password;
}
