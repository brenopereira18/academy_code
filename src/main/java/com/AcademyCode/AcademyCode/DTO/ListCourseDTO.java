package com.AcademyCode.AcademyCode.DTO;

import com.AcademyCode.AcademyCode.enums.Categories;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListCourseDTO {
    private String name;
    private Categories category;
}

