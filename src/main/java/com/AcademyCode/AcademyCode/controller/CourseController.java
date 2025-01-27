package com.AcademyCode.AcademyCode.controller;

import com.AcademyCode.AcademyCode.DTO.ListCourseDTO;
import com.AcademyCode.AcademyCode.Service.CourseService;
import com.AcademyCode.AcademyCode.enums.Category;
import com.AcademyCode.AcademyCode.model.CourseModel;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/")
    public ResponseEntity<CourseModel> create(@Valid @RequestBody CourseModel courseModel) {
        var course = courseService.create(courseModel);
        return ResponseEntity.ok().body(course);
    }

    @GetMapping("/")
    public ResponseEntity<List<ListCourseDTO>> getCoursesByFilters(@RequestParam(required = false) Category category) {
        var courses = courseService.getCoursesByFilters(category);
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(courses);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/disabled")
    public ResponseEntity<List<ListCourseDTO>> getCousersDisable() {
        var courses = courseService.getCoursesDisable();

        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(courses);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<CourseModel> update(@Valid @PathVariable UUID id, @RequestBody CourseModel courseModel) {
        CourseModel updatedCourse = courseService.update(id, courseModel);
        return ResponseEntity.ok(updatedCourse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
