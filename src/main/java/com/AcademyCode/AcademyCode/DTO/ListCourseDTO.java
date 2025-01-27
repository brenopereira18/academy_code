package com.AcademyCode.AcademyCode.DTO;

import com.AcademyCode.AcademyCode.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListCourseDTO {
    private String name;
    private Category category;
}

