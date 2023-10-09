package com.student.service;

import com.student.domain.StudentInfo;
import com.student.repository.StudentInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StudentInfoServiceImpl implements StudentInfoService {

    private final StudentInfoRepository studentInfoRepository;

    public Mono<StudentInfo> addStudentInfo(final StudentInfo studentInfo) {
        return studentInfoRepository.save(studentInfo);
    }

    public Mono<StudentInfo> retrieveStudentInfo(final String studentId) {
        Mono<StudentInfo> studentInfoMono = studentInfoRepository.findById(studentId);
        return studentInfoMono;
    }

    @Override
    public Flux<StudentInfo> retrieveAllStudentInfo() {
        return studentInfoRepository.findAll();
    }
}