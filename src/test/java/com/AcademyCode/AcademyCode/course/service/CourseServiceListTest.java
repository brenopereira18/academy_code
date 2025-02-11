package com.AcademyCode.AcademyCode.course.service;

import com.AcademyCode.AcademyCode.modules.course.DTO.ListCourseDTO;
import com.AcademyCode.AcademyCode.modules.course.enums.Category;
import com.AcademyCode.AcademyCode.modules.course.enums.Status;
import com.AcademyCode.AcademyCode.modules.course.model.CourseModel;
import com.AcademyCode.AcademyCode.modules.course.repository.CourseRepository;
import com.AcademyCode.AcademyCode.modules.course.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceListTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void should_return_all_active_courses_in_specific_category() {
        var course1 = CourseModel.builder().category(Category.FRONT_END).build();
        var course2 = CourseModel.builder().category(Category.FRONT_END).build();
        List<CourseModel> courses = List.of(course1, course2);

        when(courseRepository.findByStatusAndCategory(Status.ACTIVE, Category.FRONT_END)).thenReturn(courses);

        List<ListCourseDTO> result = courseService.getCoursesByFilters(Category.FRONT_END);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(courseRepository, times(1)).findByStatusAndCategory(Status.ACTIVE, Category.FRONT_END);
    }

    @Test
    public void should_return_all_courses_when_the_category_is_null() {
        var course1 = CourseModel.builder().category(Category.BACK_END).name("Python Avançado").build();
        var course2 = CourseModel.builder().category(Category.FRONT_END).name("React Avançado").build();
        List<CourseModel> courses = List.of(course1, course2);

        when(courseRepository.findByStatus(Status.ACTIVE)).thenReturn(courses);

        List<ListCourseDTO> result = courseService.getCoursesByFilters(null);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(courseRepository, times(1)).findByStatus(Status.ACTIVE);
    }

    @Test
    public void should_return_empty_list_when_there_is_no_active_course() {
        when(courseRepository.findByStatus(Status.ACTIVE)).thenReturn(List.of());

        List<ListCourseDTO> result = courseService.getCoursesByFilters(null);

        assertTrue(result.isEmpty());

        verify(courseRepository, never()).findByStatus(Status.ACTIVE);
    }

    @Test
    public void should_return_all_courses_with_disable_status() {
        var course1 = CourseModel.builder().category(Category.BACK_END).status(Status.DISABLED).build();
        var course2 = CourseModel.builder().category(Category.FRONT_END).status(Status.DISABLED).build();
        List<CourseModel> courses = List.of(course1, course2);

        when(courseRepository.findByStatus(Status.DISABLED)).thenReturn(courses);

        List<ListCourseDTO> result = courseService.getCoursesDisable();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(courseRepository, times(1)).findByStatus(Status.DISABLED);
    }
}
