package com.AcademyCode.AcademyCode.controller;

import com.AcademyCode.AcademyCode.exceptions.ResourceNotFoundException;
import com.AcademyCode.AcademyCode.model.CourseModel;
import com.AcademyCode.AcademyCode.repository.CourseRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public ResponseEntity<Object> create(@Valid @RequestBody CourseModel courseModel) {
        try {
            var course = courseRepository.save(courseModel);
            return ResponseEntity.ok().body(course);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getAllCourses() {
        var courses = courseRepository.findAll();
        if (courses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum curso encontrado");
        }
        return ResponseEntity.ok().body(courses);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @PathVariable UUID id, @RequestBody CourseModel courseModel) {
        CourseModel courseToBeUpdate = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso com Id " + id + " não encontrado"));

        courseToBeUpdate.setName(courseModel.getName());
        courseToBeUpdate.setCategory(courseModel.getCategory());
        courseToBeUpdate.setStatus(courseModel.getStatus());

        CourseModel updatedCourse = courseRepository.save(courseToBeUpdate);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        CourseModel courseToBeDeleted = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso com Id " + id + " não encontrado"));

        courseRepository.delete(courseToBeDeleted);
        return ResponseEntity.noContent().build();
    }
}
