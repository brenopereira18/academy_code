package com.AcademyCode.AcademyCode.course.service;

import com.AcademyCode.AcademyCode.exceptions.ResourceNotFoundException;
import com.AcademyCode.AcademyCode.modules.course.model.CourseModel;
import com.AcademyCode.AcademyCode.modules.course.repository.CourseRepository;
import com.AcademyCode.AcademyCode.modules.course.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CourseServiceDeleteTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void should_delete_the_course() {
        var course = CourseModel.builder().id(UUID.randomUUID()).name("Golang").build();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        assertDoesNotThrow(() -> courseService.delete(course.getId()));

        verify(courseRepository, times(1)).findById(course.getId());
        verify(courseRepository, times(1)).delete(course);
    }

    @Test
    public void should_throw_ResourceNotFoundException_when_course_does_not_exist() {
        var course = CourseModel.builder().id(UUID.randomUUID()).name("React avançado").build();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
            courseService.delete(course.getId())
        );

        verify(courseRepository, times(1)).findById(course.getId());
        verify(courseRepository, never()).delete(any(CourseModel.class));
    }
}
