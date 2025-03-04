package com.academycode.academycode.modules.user.controller;

import com.academycode.academycode.modules.user.dto.AuthenticationUserDTO;
import com.academycode.academycode.modules.user.dto.LoginResponseDTO;
import com.academycode.academycode.modules.user.dto.UserProfileDTO;
import com.academycode.academycode.provider.TokenProvider;
import com.academycode.academycode.modules.user.service.UserService;
import com.academycode.academycode.modules.user.model.UserModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação de usuário", description = "Endpoints de autenticação do usuário")
public class AuthenticationUserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Operation(summary = "Login do usuário", description = "Esse método possibilita o login do usuário para acessar sua conta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = TokenProvider.class))
            }),
            @ApiResponse(responseCode = "400", description = "Usuário inválido")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody AuthenticationUserDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenProvider.generateToken((UserModel) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDTO(null));
        }
    }

    @Operation(summary = "Cadastro de usuário", description = "Esse método possibilita o usuário realizar seu cadastro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = UserModel.class))
            }),
            @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })
    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@Valid @RequestBody UserModel userModel) {
        UserModel user = userService.register(userModel);
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Atualiza perfil profissional do usuário", description = "Esse método possibilita o usuário atualizar seu perfil profissional")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = UserModel.class))
            }),
            @ApiResponse(responseCode = "400", description = "Usuário não encontrado")
    })
    @PutMapping("/profile")
    public  ResponseEntity<UserModel> updateOwnProfile(@Valid @RequestBody UserProfileDTO userProfileDTO, Principal principal) {
        UserModel user = userService.updateOwnProfile(principal.getName(), userProfileDTO);
        return ResponseEntity.ok().body(user);
    }
}
