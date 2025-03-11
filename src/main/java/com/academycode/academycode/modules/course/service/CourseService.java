package com.academycode.academycode.modules.course.service;

import com.academycode.academycode.modules.course.dto.ListCourseDTO;
import com.academycode.academycode.modules.course.enums.Category;
import com.academycode.academycode.modules.course.enums.Status;
import com.academycode.academycode.exceptions.EntityFoundException;
import com.academycode.academycode.exceptions.ResourceNotFoundException;
import com.academycode.academycode.modules.course.model.CourseModel;
import com.academycode.academycode.modules.course.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public CourseModel create(CourseModel courseModel) {
        this.courseRepository.findByName(courseModel.getName())
                .ifPresent(course -> {
                    throw new EntityFoundException("Este curso já existe.");
                });

        return courseRepository.save(courseModel);
    }

    public List<ListCourseDTO> getCoursesByFilters(Category categoy) {
        Status status = Status.ACTIVE;
        List<CourseModel> courses;
        if (categoy != null) {
            courses = courseRepository.findByStatusCourseAndCategory(status, categoy);
        } else {
            courses = courseRepository.findByStatusCourse(status);
        }

        return courses.stream().map(course ->
                new ListCourseDTO(course.getName(), course.getCategory())
        ).toList();
    }

    public List<ListCourseDTO> getCoursesDisable() {
        List<CourseModel> courses = courseRepository.findByStatusCourse(Status.DISABLED);

        return courses.stream().map(course ->
                new ListCourseDTO(course.getName(), course.getCategory())
        ).toList();
    }

    @Transactional
    public CourseModel update(UUID id, CourseModel courseModel) {
        CourseModel courseToBeUpdate = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso com Id " + id + " não encontrado"));

        courseToBeUpdate.setName(courseModel.getName());
        courseToBeUpdate.setCategory(courseModel.getCategory());
        courseToBeUpdate.setStatusCourse(courseModel.getStatusCourse());

        return courseRepository.save(courseToBeUpdate);
    }

    public void delete(UUID id) {
        CourseModel courseToBeDeleted = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso com Id " + id + " não encontrado"));

        courseRepository.delete(courseToBeDeleted);
    }
}
