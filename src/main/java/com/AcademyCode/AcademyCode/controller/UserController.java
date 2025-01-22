package com.AcademyCode.AcademyCode.controller;

import com.AcademyCode.AcademyCode.DTO.UserRoleDTO;
import com.AcademyCode.AcademyCode.Service.UserService;
import com.AcademyCode.AcademyCode.model.UserModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable UUID id) {
        UserModel user = userService.getUser(id);
        return ResponseEntity.ok().body(user);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(users);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUserRole(@Valid @PathVariable UUID id, @RequestBody UserRoleDTO userRoleDTO) {
        UserModel updatedUser = userService.updateUserRole(id, userRoleDTO);
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
