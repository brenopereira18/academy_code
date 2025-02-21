package com.academycode.academycode.user.integration;

import com.academycode.academycode.modules.user.enums.UserRole;
import com.academycode.academycode.userProfile.integration.BaseIntegrationUserProfileTest;
import com.academycode.academycode.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

public class GetUserIntegrationTest extends BaseIntegrationUserProfileTest {

    @Test
    public void should_return_a_user() throws Exception {
        var userManager = TestUtils.createUser("manager", "managertest", "123456789", UserRole.MANAGER);
        userRepository.saveAndFlush(userManager);

        var user = TestUtils.createUser("user", "usertest", "123456789", null);
        userRepository.saveAndFlush(user);

        mvc.perform(MockMvcRequestBuilders.get("/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TestUtils.generateToken(userManager, "my-secret-key"))
            ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("user"));
    }

    @Test
    public void should_list_all_user() throws Exception {
        var userManager = TestUtils.createUser("manager", "managertest", "123456789", UserRole.MANAGER);
        userRepository.saveAndFlush(userManager);

        var user = TestUtils.createUser("user", "usertest", "123456789", null);
        userRepository.saveAndFlush(user);
        var user2 = TestUtils.createUser("user2", "usertest2", "123456789", null);
        userRepository.saveAndFlush(user2);

        mvc.perform(MockMvcRequestBuilders.get("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TestUtils.generateToken(userManager, "my-secret-key"))
            ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
            .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("user2"));
    }

    @Test
    public void should_return_not_found_if_user_not_exist() throws Exception {
        var userManager = TestUtils.createUser("manager", "managertest", "123456789", UserRole.MANAGER);
        userRepository.saveAndFlush(userManager);

        mvc.perform(MockMvcRequestBuilders.get("/user/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TestUtils.generateToken(userManager, "my-secret-key"))
            ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
