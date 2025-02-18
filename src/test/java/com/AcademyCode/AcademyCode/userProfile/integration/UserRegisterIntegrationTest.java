package com.AcademyCode.AcademyCode.userProfile.integration;

import com.AcademyCode.AcademyCode.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.AcademyCode.AcademyCode.utils.TestUtils.objectToJSON;

public class UserRegisterIntegrationTest extends BaseIntegrationUserProfileTest {

    @Test
    public void should_create_user() throws Exception {
        var user = TestUtils.createUser("test", "test123", "123456789", null);

        mvc.perform(MockMvcRequestBuilders.post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectToJSON(user))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void should_return_exception_if_user_exist() throws Exception {
        var user = TestUtils.createUser("test", "test123", "123456789", null);
        userRepository.saveAndFlush(user);

        mvc.perform(MockMvcRequestBuilders.post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectToJSON(user))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
