package com.AcademyCode.AcademyCode.Service;

import com.AcademyCode.AcademyCode.DTO.ListCourseDTO;
import com.AcademyCode.AcademyCode.enums.Category;
import com.AcademyCode.AcademyCode.enums.Status;
import com.AcademyCode.AcademyCode.exceptions.EntityFoundException;
import com.AcademyCode.AcademyCode.exceptions.ResourceNotFoundException;
import com.AcademyCode.AcademyCode.model.CourseModel;
import com.AcademyCode.AcademyCode.repository.CourseRepository;
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
            courses = courseRepository.findByStatusAndCategory(status, categoy);
        } else {
            courses = courseRepository.findByStatus(status);
        }

        return courses.stream().map(course ->
                new ListCourseDTO(course.getName(), course.getCategory())
        ).toList();
    }

    public List<ListCourseDTO> getCoursesDisable() {
        List<CourseModel> courses = courseRepository.findByStatus(Status.DISABLED);

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
        courseToBeUpdate.setStatus(courseModel.getStatus());

        return courseRepository.save(courseToBeUpdate);
    }

    public void delete(UUID id) {
        CourseModel courseToBeDeleted = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso com Id " + id + " não encontrado"));

        courseRepository.delete(courseToBeDeleted);
    }
}
