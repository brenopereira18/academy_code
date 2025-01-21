package com.AcademyCode.AcademyCode.repository;

import com.AcademyCode.AcademyCode.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findById(UUID uuid);
    Optional<UserModel> findByUsername(String username);
    UserDetails findByLogin(String login);
}
