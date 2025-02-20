package com.AcademyCode.AcademyCode.user.integration;

import com.AcademyCode.AcademyCode.modules.user.enums.UserRole;
import com.AcademyCode.AcademyCode.userProfile.integration.BaseIntegrationUserProfileTest;
import com.AcademyCode.AcademyCode.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

public class UserDeleteIntegrationTest extends BaseIntegrationUserProfileTest {

    @Test
    public void should_delete_a_user() throws Exception {
        var userManager = TestUtils.createUser("manager", "managertest", "123456789", UserRole.MANAGER);
        userRepository.saveAndFlush(userManager);

        var user = TestUtils.createUser("user", "usertest", "123456789", null);
        userRepository.saveAndFlush(user);

        mvc.perform(MockMvcRequestBuilders.delete("/user/" + user.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", TestUtils.generateToken(userManager, "my-secret-key"))
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void should_return_not_found_if_user_not_exist() throws Exception {
        var userManager = TestUtils.createUser("manager", "managertest", "123456789", UserRole.MANAGER);
        userRepository.saveAndFlush(userManager);

        mvc.perform(MockMvcRequestBuilders.delete("/user/" + UUID.randomUUID())
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", TestUtils.generateToken(userManager, "my-secret-key"))
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
