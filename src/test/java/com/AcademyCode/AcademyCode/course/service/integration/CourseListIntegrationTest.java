package com.AcademyCode.AcademyCode.course.service.integration;

import com.AcademyCode.AcademyCode.course.service.utils.TestUtils;
import com.AcademyCode.AcademyCode.modules.course.enums.Category;
import com.AcademyCode.AcademyCode.modules.course.enums.Status;
import com.AcademyCode.AcademyCode.modules.course.model.CourseModel;
import com.AcademyCode.AcademyCode.modules.user.enums.UserRole;
import com.AcademyCode.AcademyCode.modules.user.model.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CourseListIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_list_the_active_courses_by_category() throws Exception {
        var course1 = TestUtils.createCourse("Typescript + React", Category.FRONT_END, null);
        var course2 = TestUtils.createCourse("Java + React", Category.FULL_STACK, null);
        courseRepository.saveAndFlush(course1);
        courseRepository.saveAndFlush(course2);

        var user = TestUtils.createUser("Breno", "brenopereira", "123456789", null);
        user = userRepository.saveAndFlush(user);

        mvc.perform(MockMvcRequestBuilders.get("/course/")
                .param("category", "FULL_STACK")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TestUtils.generateToken(user, "my-secret-key"))
            ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Java + React"));
    }

    @Test
    public void should_list_all_the_active_courses_if_it_is_not_passed_category() throws Exception {
        var course1 = TestUtils.createCourse("Typescript + React", Category.FRONT_END, null);
        var course2 = TestUtils.createCourse("Java + React", Category.FULL_STACK, null);
        courseRepository.saveAndFlush(course1);
        courseRepository.saveAndFlush(course2);

        var user = TestUtils.createUser("Breno", "brenopereira", "123456789", null);
        user = userRepository.saveAndFlush(user);

        mvc.perform(MockMvcRequestBuilders.get("/course/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TestUtils.generateToken(user, "my-secret-key"))
            ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Typescript + React"));
    }

    @Test
    public void should_return_without_content_if_there_is_no_course() throws Exception {
        var user = TestUtils.createUser("Breno", "brenopereira", "123456789", null);
        user = userRepository.saveAndFlush(user);

        mvc.perform(MockMvcRequestBuilders.get("/course/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TestUtils.generateToken(user, "my-secret-key"))
            ).andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    public void should_list_the_disable_courses() throws Exception {
        var course1 = TestUtils.createCourse("Typescript + React", Category.FRONT_END, null);
        var course2 = TestUtils.createCourse("Java + React", Category.FULL_STACK, Status.DISABLED);
        courseRepository.saveAndFlush(course1);
        courseRepository.saveAndFlush(course2);

        var user = TestUtils.createUser("Breno", "brenopereira", "123456789", UserRole.ADMIN);
        user = userRepository.saveAndFlush(user);

        mvc.perform(MockMvcRequestBuilders.get("/course/disabled")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", TestUtils.generateToken(user, "my-secret-key"))
        ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Java + React"));

    }

    @Test
    public void should_return_without_authorization_if_a_normal_user_tries_to_access_disabled_courses() throws Exception {
        var course1 = TestUtils.createCourse("Typescript + React", Category.FRONT_END, Status.DISABLED);
        courseRepository.saveAndFlush(course1);

        var user = TestUtils.createUser("Breno", "brenopereira", "123456789", null);
        user = userRepository.saveAndFlush(user);

        mvc.perform(MockMvcRequestBuilders.get("/course/disabled")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TestUtils.generateToken(user, "my-secret-key"))
            ).andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
