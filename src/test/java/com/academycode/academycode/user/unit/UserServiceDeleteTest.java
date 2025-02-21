package com.academycode.academycode.user.unit;

import com.academycode.academycode.modules.user.service.UserService;
import com.academycode.academycode.modules.user.repository.UserRepository;
import com.academycode.academycode.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceDeleteTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void should_delete_a_user() {
        var user = TestUtils.createUser("user", "usertest", "123456789", null);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.delete(user.getId()));
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).delete(user);
    }
}
