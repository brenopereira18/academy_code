package com.academycode.academycode.user.unit;

import com.academycode.academycode.modules.course.enums.Status;
import com.academycode.academycode.modules.user.dto.UserRoleDTO;
import com.academycode.academycode.modules.user.service.UserService;
import com.academycode.academycode.modules.user.enums.UserRole;
import com.academycode.academycode.modules.user.model.UserModel;
import com.academycode.academycode.modules.user.repository.UserRepository;
import com.academycode.academycode.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUpdateRoleTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void should_update_the_user_role() {
        var user = TestUtils.createUser("user", "usertest", "123456789", null);
        var role = new UserRoleDTO(UserRole.ADMIN, Status.ACTIVE);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var userUpdate = userService.updateUserRole(user.getId(), role);

        assertNotNull(userUpdate);
        assertEquals(UserRole.ADMIN, userUpdate.getRole());
        verify(userRepository).findById(user.getId());
        verify(userRepository).save(user);
    }
}
