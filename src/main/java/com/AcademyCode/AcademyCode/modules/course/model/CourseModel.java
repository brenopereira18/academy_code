package com.AcademyCode.AcademyCode.modules.course.model;

import com.AcademyCode.AcademyCode.modules.course.enums.Category;
import com.AcademyCode.AcademyCode.modules.course.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "course")
@Table(name = "course")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank()
    @Column(nullable = false, length = 100, unique = true)
    @Schema(description = "Nome da empresa", example = "Fundamentos do Spring Boot")
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;
}
