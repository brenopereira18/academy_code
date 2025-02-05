package com.AcademyCode.AcademyCode.modules.user.DTO;

import com.AcademyCode.AcademyCode.modules.course.enums.Status;
import com.AcademyCode.AcademyCode.modules.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRoleDTO {
    private UserRole role;
    private Status status;
}
