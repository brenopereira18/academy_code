package com.AcademyCode.AcademyCode.course.service.integration;

import com.AcademyCode.AcademyCode.course.service.utils.TestUtils;
import com.AcademyCode.AcademyCode.modules.course.enums.Category;
import com.AcademyCode.AcademyCode.modules.course.model.CourseModel;
import com.AcademyCode.AcademyCode.modules.user.enums.UserRole;
import com.AcademyCode.AcademyCode.modules.user.model.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.AcademyCode.AcademyCode.course.service.utils.TestUtils.objectToJSON;

public class CourseCreateIntegrationTest extends BaseIntegrationTest {

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
