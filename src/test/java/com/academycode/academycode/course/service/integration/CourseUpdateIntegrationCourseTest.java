package com.academycode.academycode.course.service.integration;

import com.academycode.academycode.utils.TestUtils;
import com.academycode.academycode.modules.course.enums.Category;
import com.academycode.academycode.modules.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static com.academycode.academycode.utils.TestUtils.objectToJSON;

public class CourseUpdateIntegrationCourseTest extends BaseIntegrationCourseTest {

    @Test
    public void should_update_course_if_exist() throws Exception {
        var course = TestUtils.createCourse("Typescript + React", Category.FRONT_END, null);
        courseRepository.saveAndFlush(course);

        var user = TestUtils.createUser("Breno", "brenopereira", "123456789", UserRole.ADMIN);
        user = userRepository.saveAndFlush(user);

        course.setName("React avançado");

        mvc.perform(MockMvcRequestBuilders.put("/course/" + course.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJSON(course))
                .header("Authorization", TestUtils.generateToken(user, "my-secret-key"))
            ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("React avançado"));
    }

    @Test
    public void should_return_not_found_if_course_not_exist() throws Exception {
        var user = TestUtils.createUser("Breno", "brenopereira", "123456789", UserRole.ADMIN);
        user = userRepository.saveAndFlush(user);

        var course = TestUtils.createCourse("Typescript + React", Category.FRONT_END, null);

        mvc.perform(MockMvcRequestBuilders.put("/course/" + UUID.randomUUID())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectToJSON(course))
            .header("Authorization", TestUtils.generateToken(user, "my-secret-key"))
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void should_return_forbidden_if_user_is_not_authorized_to_update_course() throws Exception {
        var user = TestUtils.createUser("Breno", "brenopereira", "123456789", null);
        user = userRepository.saveAndFlush(user);

        var course = TestUtils.createCourse("Typescript + React", Category.FRONT_END, null);
        courseRepository.saveAndFlush(course);

        course.setName("React avançado");

        mvc.perform(MockMvcRequestBuilders.put("/course/" + course.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectToJSON(course))
            .header("Authorization", TestUtils.generateToken(user, "my-secret-key"))
        ).andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
