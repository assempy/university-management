
package com.university.universitymanagement.grade.controller;

import com.university.universitymanagement.grade.entity.Grade;
import com.university.universitymanagement.grade.service.GradeService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping
    public ResponseEntity<Grade> createGrade(
            @Valid @RequestBody Grade grade) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gradeService.createGrade(grade));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<List<Grade>> getGradesByStudentId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                gradeService.getGradesByStudentId(id)
        );
    }
}
