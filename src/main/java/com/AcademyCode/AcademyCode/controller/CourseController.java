package com.AcademyCode.AcademyCode.controller;

import com.AcademyCode.AcademyCode.DTO.ListCourseDTO;
import com.AcademyCode.AcademyCode.Service.CourseService;
import com.AcademyCode.AcademyCode.model.CourseModel;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/")
    public ResponseEntity<CourseModel> create(@Valid @RequestBody CourseModel courseModel) {
        var course = courseService.create(courseModel);
        return ResponseEntity.ok().body(course);
    }

    @GetMapping("/")
    public ResponseEntity<List<ListCourseDTO>> getAllCourses() {
        var courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(courses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseModel> update(@Valid @PathVariable UUID id, @RequestBody CourseModel courseModel) {
        CourseModel updatedCourse = courseService.update(id, courseModel);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
