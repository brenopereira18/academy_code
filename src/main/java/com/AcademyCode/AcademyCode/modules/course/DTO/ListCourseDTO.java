package com.AcademyCode.AcademyCode.modules.course.DTO;

import com.AcademyCode.AcademyCode.modules.course.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListCourseDTO {

    @Schema(description = "Nome do usuário", example = "João")
    private String name;
    private Category category;
}

