package com.academycode.academycode.course.service.integration;

import com.academycode.academycode.utils.TestUtils;
import com.academycode.academycode.modules.course.enums.Category;
import com.academycode.academycode.modules.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.academycode.academycode.utils.TestUtils.objectToJSON;

public class CourseCreateIntegrationCourseTest extends BaseIntegrationCourseTest {

    @Test
    public void should_create_a_course_successfully() throws Exception {
        var user = TestUtils.createUser("Breno", "brenopereira", "123456789", UserRole.ADMIN);
        user = userRepository.saveAndFlush(user);

        var course = TestUtils.createCourse("Typescript + React", Category.FRONT_END, null);

        mvc.perform(MockMvcRequestBuilders.post("/course/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectToJSON(course))
            .header("Authorization", TestUtils.generateToken(user, "my-secret-key"))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void should_return_error_if_course_already_exist() throws Exception {
        var user = TestUtils.createUser("Breno", "brenopereira", "123456789", UserRole.ADMIN);
        user = userRepository.saveAndFlush(user);

        var course = TestUtils.createCourse("Typescript + React", Category.FRONT_END, null);
        courseRepository.saveAndFlush(course);

        var duplicateCourse = TestUtils.createCourse("Typescript + React", Category.FRONT_END, null);

        mvc.perform(MockMvcRequestBuilders.post("/course/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectToJSON(duplicateCourse))
            .header("Authorization", TestUtils.generateToken(user, "my-secret-key"))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void should_return_error_if_user_has_not_authorized_to_create_course() throws Exception {
        var user = TestUtils.createUser("Breno", "brenopereira", "123456789", null);
        user = userRepository.saveAndFlush(user);

        var course = TestUtils.createCourse("Typescript + React", Category.FRONT_END, null);

        mvc.perform(MockMvcRequestBuilders.post("/course/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectToJSON(course))
            .header("Authorization", TestUtils.generateToken(user, "my-secret-key"))
        ).andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
