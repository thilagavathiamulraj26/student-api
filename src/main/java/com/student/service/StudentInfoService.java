package com.student.service;

import com.student.domain.StudentInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentInfoService {

    Mono<StudentInfo> addStudentInfo(final StudentInfo studentInfo);

    Mono<StudentInfo> retrieveStudentInfo(final String studentId);

    Flux<StudentInfo> retrieveAllStudentInfo();
}
