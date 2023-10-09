package com.student.repository;

import com.student.domain.StudentInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ActiveProfiles("test")
public class StudentInfoRepositoryIntgTest {
    @Autowired
    StudentInfoRepository studentInfoRepository;

    @BeforeEach
    void setUp() {

        var studentInfos = List.of(
                new StudentInfo("1", "John", "Peter", 6, LocalDate.parse("2017-05-04"), "First Grade"),
                new StudentInfo("2", "Steve", "Anthony", 6, LocalDate.parse("2016-05-04"), "Second Grade"));

        studentInfoRepository.saveAll(studentInfos)
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        studentInfoRepository.deleteAll().block();
    }

    @Test
    void findAll() {
        var studentInfoFlux = studentInfoRepository.findAll().log();

        StepVerifier.create(studentInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void findById() {

        var studentInfo = studentInfoRepository.findById("1");

        StepVerifier.create(studentInfo)
                .assertNext(studentInfo1 -> {
                    assertEquals("John", studentInfo1.getFirstName());
                });
    }

    @Test
    void saveStudentInfo() {

        var studentInfo = new StudentInfo("3", "Alexis", "Pope", 7, LocalDate.parse("2015-05-04"), "Third Grade");;

        var savedStudentInfo = studentInfoRepository.save(studentInfo);

        StepVerifier.create(savedStudentInfo)
                .assertNext(studentInfo1 -> {
                    assertNotNull(studentInfo.getStudentId());
                });
    }

    @Test
    void updateStudentInfo() {

        var studentInfo = studentInfoRepository.findById("1").block();
        studentInfo.setFirstName("Johny");

        var updatedStudentInfo = studentInfoRepository.save(studentInfo);

        StepVerifier.create(updatedStudentInfo)
                .assertNext(studentInfo1 -> {
                    assertNotNull(studentInfo1.getStudentId());
                    assertEquals("Johny", studentInfo1.getFirstName());
                });
    }

    @Test
    void deleteStudentInfo() {

        studentInfoRepository.deleteById("1").block();

        var studentInfos = studentInfoRepository.findAll();

        StepVerifier.create(studentInfos)
                .expectNextCount(1)
                .verifyComplete();
    }
}
