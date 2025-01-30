package com.AcademyCode.AcademyCode.controller;

import com.AcademyCode.AcademyCode.DTO.ListCourseDTO;
import com.AcademyCode.AcademyCode.Service.CourseService;
import com.AcademyCode.AcademyCode.enums.Category;
import com.AcademyCode.AcademyCode.model.CourseModel;

import com.AcademyCode.AcademyCode.model.UserModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course")
@Tag(name = "Curso", description = "Endpoints de gerenciamento dos cursos")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Operation(summary = "Cria curso", description = "Esse método cria um curso, podendo ser criado por um MANAGER ou ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = CourseModel.class))
            }),
            @ApiResponse(responseCode = "400", description = "Curso já existe")
    })
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/")
    public ResponseEntity<CourseModel> create(@Valid @RequestBody CourseModel courseModel) {
        var course = courseService.create(courseModel);
        return ResponseEntity.ok().body(course);
    }

    @Operation(summary = "Lista todos os cursos, podendo passar filtro", description = "Esse método lista todos os cursos podendo ou não passar a categoria como filtro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = CourseModel.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Não existe nenhum curso cadastrado")
    })
    @SecurityRequirement(name = "jwt_auth")
    @GetMapping("/")
    public ResponseEntity<List<ListCourseDTO>> getCoursesByFilters(@RequestParam(required = false) Category category) {
        var courses = courseService.getCoursesByFilters(category);
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(courses);
    }

    @Operation(summary = "Lista todos os cursos com status de desativado", description = "Esse método lista todos os cursos com status de desativado, acessível para MANAGER e ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = CourseModel.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Não existe nenhum curso como desativado")
    })
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/disabled")
    public ResponseEntity<List<ListCourseDTO>> getCousersDisable() {
        var courses = courseService.getCoursesDisable();

        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(courses);
    }

    @Operation(summary = "Atualiza curso", description = "Esse método é responsavel por atualizar um curso, acessivel somente para MANAGER e ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CourseModel.class))
            }),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<CourseModel> update(@Valid @PathVariable UUID id, @RequestBody CourseModel courseModel) {
        CourseModel updatedCourse = courseService.update(id, courseModel);
        return ResponseEntity.ok(updatedCourse);
    }

    @Operation(summary = "Deleta curso", description = "Esse método é responsavel por deletar um curso, acessivel somente para MANAGER e ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Curso deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    @SecurityRequirement(name = "jwt_auth")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
