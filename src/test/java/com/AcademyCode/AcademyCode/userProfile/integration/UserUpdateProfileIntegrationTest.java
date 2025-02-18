package com.AcademyCode.AcademyCode.userProfile.integration;

import com.AcademyCode.AcademyCode.modules.user.DTO.UserProfileDTO;
import com.AcademyCode.AcademyCode.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.AcademyCode.AcademyCode.utils.TestUtils.objectToJSON;

public class UserUpdateProfileIntegrationTest extends BaseIntegrationUserProfileTest {

    @Test
    public void should_update_user_profile() throws Exception {
        var user = TestUtils.createUser("test", "test123", "123456789", null);
        userRepository.saveAndFlush(user);

        var updateUser = UserProfileDTO.builder().name("test update").password(user.getPassword()).build();

        mvc.perform(MockMvcRequestBuilders.put("/auth/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJSON(updateUser))
                .header("Authorization", TestUtils.generateToken(user, "my-secret-key"))
            ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test update"));
    }
}
