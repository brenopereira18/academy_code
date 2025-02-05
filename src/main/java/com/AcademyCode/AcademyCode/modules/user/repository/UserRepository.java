package com.AcademyCode.AcademyCode.modules.user.repository;

import com.AcademyCode.AcademyCode.modules.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findById(UUID uuid);
    Optional<UserModel> findByUsername(String username);
}
