package com.academycode.academycode.modules.course.repository;

import com.academycode.academycode.modules.course.enums.Category;
import com.academycode.academycode.modules.course.enums.Status;
import com.academycode.academycode.modules.course.model.CourseModel;
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
