package com.student.controller;

import com.student.domain.StudentInfo;
import com.student.repository.StudentInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class StudentInfoControllerIntgTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private StudentInfoRepository studentInfoRepository;

    static String STUDENT_INFO_URL = "/api/v1";

    @BeforeEach
    void setUp() {
        var studentInfos = List.of(
                new StudentInfo("1", "John", "Peter", 6, LocalDate.parse("2017-05-04"), "First Grade"),
                new StudentInfo("2", "Steve", "Anthony", 6, LocalDate.parse("2016-05-04"), "Second Grade"));

        studentInfoRepository
                .deleteAll()
                .thenMany(studentInfoRepository.saveAll(studentInfos))
                .blockLast();
    }

    @Test
    void retrieveStudentInfo() {
        var id = "1";

        webTestClient
                .get()
                .uri(STUDENT_INFO_URL+"/students/{id}", id)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(StudentInfo.class)
                .consumeWith(studentInfoEntityExchangeResult -> {
                    var retrievedstudentInfo = studentInfoEntityExchangeResult.getResponseBody();
                    assert retrievedstudentInfo != null;
                    assert retrievedstudentInfo.getStudentId() != null;
                });
    }

    @Test
    void retrieveAllStudentInfo() {

        webTestClient
                .get()
                .uri(STUDENT_INFO_URL+"/students")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(StudentInfo.class)
                .hasSize(2);
    }

    @Test
    void addStudentInfo() {
        var studentInfo = new StudentInfo("3", "Alexis", "Pope", 7, LocalDate.parse("2015-05-04"), "Third Grade");

        webTestClient
                .post()
                .uri(STUDENT_INFO_URL+"/students")
                .bodyValue(studentInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(StudentInfo.class)
                .consumeWith(studentInfoEntityExchangeResult -> {
                    var savedStudentInfo = studentInfoEntityExchangeResult.getResponseBody();
                    assert Objects.requireNonNull(savedStudentInfo).getStudentId() != null;
                });
    }

    @Test
    void updateStudentInfo() {
        var id = "1";
        var updatedStudentInfo = new StudentInfo("1", "Johny", "Peter", 6, LocalDate.parse("2017-05-04"), "First Grade");

        webTestClient
                .put()
                .uri(STUDENT_INFO_URL+"/students/{id}", id)
                .bodyValue(updatedStudentInfo)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(StudentInfo.class)
                .consumeWith(studentInfoEntityExchangeResult -> {
                    var studentInfo = studentInfoEntityExchangeResult.getResponseBody();
                    assert studentInfo != null;
                    assertEquals("Johny", studentInfo.getFirstName());
                });
    }

    @Test
    void deleteStudentInfo() {
        var id = "1";

        webTestClient
                .delete()
                .uri(STUDENT_INFO_URL+"/students/{id}", id)
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}
