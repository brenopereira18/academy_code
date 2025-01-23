package com.AcademyCode.AcademyCode.controller;

import com.AcademyCode.AcademyCode.DTO.AuthenticationDTO;
import com.AcademyCode.AcademyCode.DTO.UserProfileDTO;
import com.AcademyCode.AcademyCode.Service.UserService;
import com.AcademyCode.AcademyCode.model.UserModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthenticationUserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().body(auth);
    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@Valid @RequestBody UserModel userModel) {
        UserModel user = userService.register(userModel);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/profile")
    public  ResponseEntity<UserModel> updateOwnProfile(@Valid @RequestBody UserProfileDTO userProfileDTO, Principal principal) {
        UserModel user = userService.updateOwnProfile(principal.getName(), userProfileDTO);
        return ResponseEntity.ok().body(user);
    }
}
