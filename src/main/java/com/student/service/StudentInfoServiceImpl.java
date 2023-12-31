package com.student.service;

import com.student.domain.StudentInfo;
import com.student.exception.StudentNotFoundException;
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
        return studentInfoRepository.findById(studentId)
                .switchIfEmpty(Mono.error(new StudentNotFoundException("Student Not Found for "+ studentId)));
    }

    @Override
    public Flux<StudentInfo> retrieveAllStudentInfo() {
        return studentInfoRepository.findAll();
    }

    @Override
    public Mono<StudentInfo> updateStudentInfo(final StudentInfo studentInfo, final String studentId) {
        return studentInfoRepository.findById(studentId)
                .switchIfEmpty(Mono.error(new StudentNotFoundException("Student Not Found for "+ studentId)))
                .map(student -> {
                    studentInfo.setStudentId(studentId);
                    return studentInfo;
                })
                .flatMap(studentInfoRepository::save);
    }

    @Override
    public Mono<Void> deleteStudentInfo(final String studentId) {
        return studentInfoRepository.deleteById(studentId);
    }
}
