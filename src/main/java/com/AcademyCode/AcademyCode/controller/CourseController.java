package com.AcademyCode.AcademyCode.controller;

import com.AcademyCode.AcademyCode.model.CourseModel;
import com.AcademyCode.AcademyCode.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody CourseModel courseModel) {
        try {
            var course = courseRepository.save(courseModel);
            return ResponseEntity.ok().body(course);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<CourseModel>> getAllCourses() {
        try {
            var courses = courseRepository.findAll();
            return ResponseEntity.ok().body(courses);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody CourseModel courseModel) {
        try {
            CourseModel courseToBeUpdate = courseRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException());

            courseToBeUpdate.setName(courseModel.getName());
            courseToBeUpdate.setCategory(courseModel.getCategory());
            courseToBeUpdate.setStatus(courseModel.getStatus());

            CourseModel updatedCourse = courseRepository.save(courseToBeUpdate);
            return ResponseEntity.ok(updatedCourse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            CourseModel courseToBeDeleted = courseRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException());

            courseRepository.delete(courseToBeDeleted);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
