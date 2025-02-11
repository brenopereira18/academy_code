package com.AcademyCode.AcademyCode.course.service;

import com.AcademyCode.AcademyCode.exceptions.EntityFoundException;
import com.AcademyCode.AcademyCode.modules.course.model.CourseModel;
import com.AcademyCode.AcademyCode.modules.course.repository.CourseRepository;
import com.AcademyCode.AcademyCode.modules.course.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CourseServiceCreateTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void should_create_a_course_when_it_not_exist() {
        var newCourse = CourseModel.builder().name("Java avançado").build();

        when(courseRepository.findByName(newCourse.getName())).thenReturn(Optional.empty());
        when(courseRepository.save(newCourse)).thenReturn(newCourse);

        CourseModel courseModel = courseService.create(newCourse);
        assertNotNull(courseModel);
        assertEquals("Java avançado", newCourse.getName());

        verify(courseRepository, times(1)).findByName(newCourse.getName());
        verify(courseRepository, times(1)).save(any(CourseModel.class));
    }

    @Test
    public void should_make_an_exception_when_the_course_exists() {
        var existingCourse = CourseModel.builder().name("Java avançado").build();

        when(courseRepository.findByName("Java avançado")).thenReturn(Optional.of(existingCourse));

        assertThrows(EntityFoundException.class, () ->
            courseService.create(existingCourse)
        );

        verify(courseRepository, times(1)).findByName(existingCourse.getName());
        verify(courseRepository, never()).save(any(CourseModel.class));
    }
}
