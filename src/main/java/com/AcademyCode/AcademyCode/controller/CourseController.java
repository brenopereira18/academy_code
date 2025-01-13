package com.AcademyCode.AcademyCode.controller;

import com.AcademyCode.AcademyCode.model.CourseModel;
import com.AcademyCode.AcademyCode.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/")
    public CourseModel create(@RequestBody CourseModel courseModel) {
        try {
            return courseRepository.save(courseModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/list")
    public List<CourseModel> getAllCourses() {
        try {
            return courseRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
