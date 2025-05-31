package com.sam.springdatajpawithuploadanddownload.service;


import com.sam.springdatajpawithuploadanddownload.DTO.APIResponse;
import com.sam.springdatajpawithuploadanddownload.DTO.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface StudentService {


    Student saveStudent(Student student);

    List<Student> getStudents();

    void deleteStudent(Integer studentId);

    Student updateStudent(Student student);

    APIResponse uploadStudentDoc(MultipartFile file, Integer studentId);

    Map<String, Object> downloadStudentDoc(Integer studentId) throws IOException;

    List<Student> searchStudents(String name);
}
