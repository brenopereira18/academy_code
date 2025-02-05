package com.AcademyCode.AcademyCode.modules.user.controller;

import com.AcademyCode.AcademyCode.modules.user.DTO.UserRoleDTO;
import com.AcademyCode.AcademyCode.modules.user.Service.UserService;
import com.AcademyCode.AcademyCode.modules.user.model.UserModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Tag(name = "Usuário", description = "Endpoints de gerenciamento de usuário para um MANEGER")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Informações de um usuário específico", description = "Esse método retorna dados da conta de um usuário específico, podendo ser consultado apenas por um MANAGER")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = UserModel.class))
            }),
            @ApiResponse(responseCode = "404", description = "Usuário com este id não foi encontrado")
    })
    @SecurityRequirement(name = "jwt_auth")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<UserModel> getUser(@PathVariable UUID id) {
        UserModel user = userService.getUser(id);
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Lista de todos os usuários", description = "Esse método retorna uma lista de todos os usuários com seus respectivos dados, podendo ser consultado apenas por um MANAGER")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = UserModel.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Não possui nenhum usuário")
    })
    @SecurityRequirement(name = "jwt_auth")
    @GetMapping("/")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(users);
    }

    @Operation(summary = "Atualiza status e o tipo de usuario", description = "Esse método atualiza o status e tipo do usuário, podendo ser atualizado somente por um MANAGER")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = UserModel.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Usuário com este id não foi encontrado")
    })
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUserRole(@Valid @PathVariable UUID id, @RequestBody UserRoleDTO userRoleDTO) {
        UserModel updatedUser = userService.updateUserRole(id, userRoleDTO);
        return ResponseEntity.ok().body(updatedUser);
    }

    @Operation(summary = "Deleta um usuario", description = "Esse método deleta um usuário, podendo ser deletado somente por um MANAGER")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário deletado"),
            @ApiResponse(responseCode = "404", description = "Usuário com este id não foi encontrado")
    })
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
