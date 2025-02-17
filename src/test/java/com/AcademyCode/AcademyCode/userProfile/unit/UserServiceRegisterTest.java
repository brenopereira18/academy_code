package com.AcademyCode.AcademyCode.userProfile.unit;

import com.AcademyCode.AcademyCode.exceptions.EntityFoundException;
import com.AcademyCode.AcademyCode.modules.user.Service.UserService;
import com.AcademyCode.AcademyCode.modules.user.model.UserModel;
import com.AcademyCode.AcademyCode.modules.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceRegisterTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void should_create_user_if_not_exist() {
        var user = UserModel.builder().name("Breno").username("brenopereira").password("123456789").build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        UserModel createdUser = userService.register(user);
        assertNotNull(createdUser);
        assertEquals("Breno", createdUser.getName());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, times(1)).save(any(UserModel.class));
    }

    @Test
    public void should_make_an_exception_when_the_user_exists() {
        var user = UserModel.builder().name("Breno").username("brenopereira").password("123456789").build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        assertThrows(EntityFoundException.class, () ->
            userService.register(user)
        );

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, never()).save(any(UserModel.class));
    }
}
