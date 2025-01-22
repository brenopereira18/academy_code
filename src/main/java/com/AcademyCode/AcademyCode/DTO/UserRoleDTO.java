package com.AcademyCode.AcademyCode.DTO;

import com.AcademyCode.AcademyCode.enums.Status;
import com.AcademyCode.AcademyCode.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRoleDTO {
    private UserRole role;
    private Status status;
}
