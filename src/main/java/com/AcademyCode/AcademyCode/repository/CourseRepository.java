package com.AcademyCode.AcademyCode.repository;

import com.AcademyCode.AcademyCode.enums.Category;
import com.AcademyCode.AcademyCode.enums.Status;
import com.AcademyCode.AcademyCode.model.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseModel, UUID> {
    Optional<CourseModel> findById(UUID id);
    Optional<CourseModel> findByName(String name);
    List<CourseModel> findByStatusAndCategory(Status status, Category category);
    List<CourseModel> findByStatus(Status status);
}
