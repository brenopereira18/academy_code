package com.academycode.academycode.user.unit;

import com.academycode.academycode.exceptions.ResourceNotFoundException;
import com.academycode.academycode.modules.user.service.UserService;
import com.academycode.academycode.modules.user.model.UserModel;
import com.academycode.academycode.modules.user.repository.UserRepository;
import com.academycode.academycode.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetUserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void should_return_a_user() {
        var user = TestUtils.createUser("user", "usertest", "123456789", null);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserModel getUser = userService.getUser(user.getId());

        assertNotNull(getUser);
        assertEquals("user", getUser.getName());
        verify(userRepository).findById(user.getId());
    }

    @Test
    public void should_list_all_users() {
        var user = TestUtils.createUser("user", "usertest", "123456789", null);
        var user2 = TestUtils.createUser("user2", "user2test", "123456789", null);
        var user3 = TestUtils.createUser("user3", "user3test", "123456789", null);

        List<UserModel> users = List.of(user, user2, user3);
        when(userRepository.findAll()).thenReturn(users);

        List<UserModel> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(userRepository).findAll();
    }

    @Test
    public void should_return_exception_if_user_not_exist() {
        UUID idUser = UUID.randomUUID();
        when(userRepository.findById(idUser)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
            userService.getUser(idUser)
        );
        verify(userRepository).findById(idUser);
    }
}
