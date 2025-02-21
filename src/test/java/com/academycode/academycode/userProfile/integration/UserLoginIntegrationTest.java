package com.academycode.academycode.userProfile.integration;

import com.academycode.academycode.modules.user.dto.AuthenticationUserDTO;
import com.academycode.academycode.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.academycode.academycode.utils.TestUtils.objectToJSON;

public class UserLoginIntegrationTest extends BaseIntegrationUserProfileTest {

    @Test
    public void should_return_token_when_login_is_successful() throws Exception {
        var user = TestUtils.createUser("test", "test123", new BCryptPasswordEncoder().encode("123456789"),  null);
        userRepository.saveAndFlush(user);

        var authenticateUser = AuthenticationUserDTO.builder().username(user.getUsername()).password("123456789").build();

        mvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJSON(authenticateUser))
            ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void should_return_unauthorized_when_credentials_are_invalid() throws Exception {
        AuthenticationUserDTO loginRequest = new AuthenticationUserDTO("test123", "123456789");

        mvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJSON(loginRequest)))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
