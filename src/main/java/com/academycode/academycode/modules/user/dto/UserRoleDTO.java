package com.academycode.academycode.modules.user.dto;

import com.academycode.academycode.modules.course.enums.Status;
import com.academycode.academycode.modules.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRoleDTO {
    private UserRole role;
    private Status status;
}
