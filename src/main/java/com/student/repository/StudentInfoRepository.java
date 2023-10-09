package com.student.repository;

import com.student.domain.StudentInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StudentInfoRepository extends ReactiveMongoRepository<StudentInfo, String> {
}
