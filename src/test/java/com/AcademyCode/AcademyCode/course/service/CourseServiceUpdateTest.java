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

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceUpdateTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void should_update_an_existing_course() {
        var course = CourseModel.builder().id(UUID.randomUUID()).name("React avançado").build();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(courseRepository.save(any(CourseModel.class))).thenReturn(course);

        course.setName("Angular avançado");
        CourseModel courseUpdate = courseService.update(course.getId(), course);

        assertNotNull(courseUpdate);
        assertEquals("Angular avançado", courseUpdate.getName());

        verify(courseRepository, times(1)).findById(course.getId());
        verify(courseRepository, times(1)).save(any(CourseModel.class));
    }

    @Test
    public void should_return_a_type_ResourceNotFoundException_exception_if_the_course_not_exist() {
        var course = CourseModel.builder().id(UUID.randomUUID()).name("React avançado").build();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
            courseService.update(course.getId(), course)
        );

        verify(courseRepository, times(1)).findById(course.getId());
        verify(courseRepository, never()).save(any(CourseModel.class));
    }
}
