package com.AcademyCode.AcademyCode.user.unit;

import com.AcademyCode.AcademyCode.modules.course.enums.Status;
import com.AcademyCode.AcademyCode.modules.user.DTO.UserRoleDTO;
import com.AcademyCode.AcademyCode.modules.user.Service.UserService;
import com.AcademyCode.AcademyCode.modules.user.enums.UserRole;
import com.AcademyCode.AcademyCode.modules.user.model.UserModel;
import com.AcademyCode.AcademyCode.modules.user.repository.UserRepository;
import com.AcademyCode.AcademyCode.utils.TestUtils;
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
