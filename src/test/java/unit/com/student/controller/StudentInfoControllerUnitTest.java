package com.student.controller;

import com.student.domain.StudentInfo;
import com.student.service.StudentInfoService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = StudentInfoController.class)
@AutoConfigureWebTestClient
public class StudentInfoControllerUnitTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private StudentInfoService studentInfoServiceMock;

    private final String STUDENT_INFO_URL = "/api/v1";

    @Test
    void getAllStudentInfo() {

        var studentInfos = List.of(
                new StudentInfo("1", "John", "Peter", 6, LocalDate.parse("2017-05-04"), "First Grade"),
                new StudentInfo("2", "Steve", "Anthony", 6, LocalDate.parse("2016-05-04"), "Second Grade"));

        when(studentInfoServiceMock.retrieveAllStudentInfo()).thenReturn(Flux.fromIterable(studentInfos));

        webTestClient.get()
                .uri(STUDENT_INFO_URL+"/students")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(StudentInfo.class)
                .hasSize(2);
    }

    @Test
    void getStudentInfo() {

        var studentInfo = new StudentInfo("1", "John", "Peter", 6, LocalDate.parse("2017-05-04"), "First Grade");

        Mockito.when(studentInfoServiceMock.retrieveStudentInfo(isA(String.class))).thenReturn(Mono.just(studentInfo));

        webTestClient.get()
                .uri(STUDENT_INFO_URL+"/students/1")
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
    void addStudentInfo() {

        var studentInfo = new StudentInfo("1", "John", "Peter", 6, LocalDate.parse("2017-05-04"), "First Grade");

        Mockito.when(studentInfoServiceMock.addStudentInfo(isA(StudentInfo.class))).thenReturn(Mono.just(studentInfo));

        webTestClient.post()
                .uri(STUDENT_INFO_URL+"/students")
                .bodyValue(studentInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(StudentInfo.class)
                .consumeWith(studentInfoEntityExchangeResult -> {
                    var retrievedstudentInfo = studentInfoEntityExchangeResult.getResponseBody();
                    assert retrievedstudentInfo != null;
                    assert retrievedstudentInfo.getStudentId() != null;
                });
    }

    @Test
    void updateStudentInfo() {

        var studentInfo = new StudentInfo("1", "John", "Peter", 6, LocalDate.parse("2017-05-04"), "First Grade");

        Mockito.when(studentInfoServiceMock.updateStudentInfo(isA(StudentInfo.class), ArgumentMatchers.isA(String.class))).thenReturn(Mono.just(studentInfo));

        webTestClient.put()
                .uri(STUDENT_INFO_URL+"/students/1")
                .bodyValue(studentInfo)
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
    void deleteStudentInfo() {

        Mockito.when(studentInfoServiceMock.deleteStudentInfo(isA(String.class))).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri(STUDENT_INFO_URL+"/students/1")
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}
