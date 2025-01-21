package com.AcademyCode.AcademyCode.controller;

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

    @PostMapping("/")
    public ResponseEntity<UserModel> create(@Valid @RequestBody UserModel userModel ) {
        UserModel user = userService.create(userModel);
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

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> update(@Valid @PathVariable UUID id, @RequestBody UserModel userModel) {
        UserModel updatedUser = userService.update(id, userModel);
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
