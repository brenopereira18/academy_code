package com.AcademyCode.AcademyCode.userProfile.unit;

import com.AcademyCode.AcademyCode.modules.user.DTO.UserProfileDTO;
import com.AcademyCode.AcademyCode.modules.user.Service.UserService;
import com.AcademyCode.AcademyCode.modules.user.model.UserModel;
import com.AcademyCode.AcademyCode.modules.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUpdateProfileTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void should_update_user_profile_if_it_exist() {
        var user = UserModel.builder().name("test").username("test123").password("123456789").build();
        var userProfile = UserProfileDTO.builder().name("test update").password(user.getPassword()).build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var updateUserProfile = userService.updateOwnProfile(user.getUsername(), userProfile);

        assertNotNull(updateUserProfile);
        assertEquals("test update", updateUserProfile.getName());
    }

}
