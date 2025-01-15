package com.AcademyCode.AcademyCode.repository;

import com.AcademyCode.AcademyCode.model.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseModel, UUID> {
    Optional<CourseModel> findById(UUID id);
}
