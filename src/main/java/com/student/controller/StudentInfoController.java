package com.student.controller;

import com.student.domain.StudentInfo;
import com.student.service.StudentInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StudentInfoController {

    private final StudentInfoService studentInfoService;

    @PostMapping(value = "/students")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<StudentInfo> addStudentInfo(@RequestBody @Valid final StudentInfo studentInfo) {
        return studentInfoService.addStudentInfo(studentInfo);
    }

    @GetMapping(value = "/students/{studentId}")
    public Mono<StudentInfo> retrieveStudentInfo(@PathVariable final String studentId) {
        return studentInfoService.retrieveStudentInfo(studentId);
    }

    @GetMapping(value = "/students")
    public Flux<StudentInfo> retrieveAllStudentInfo() {
        return studentInfoService.retrieveAllStudentInfo();
    }

    @PutMapping(value = "/students/{studentId}")
    public Mono<StudentInfo> updateStudentInfo(@RequestBody @Valid final StudentInfo studentInfo, @PathVariable final String studentId) {
        return studentInfoService.updateStudentInfo(studentInfo, studentId);
    }

    @DeleteMapping(value = "/students/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteStudentInfo(@PathVariable final String studentId) {
        return studentInfoService.deleteStudentInfo(studentId);
    }
}
