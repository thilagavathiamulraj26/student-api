package com.student.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "student")
public class StudentInfo {

    @Id
    private String studentId;
    @NotBlank(message = "studentInfo.firstName must be present")
    private String firstName;
    @NotBlank(message = "studentInfo.lastName must be present")
    private String lastName;
    @NotNull
    @Positive(message = "studentInfo.age must be positive")
    private Integer age;
    private LocalDate dateOfBirth;
    private String grade;
}
