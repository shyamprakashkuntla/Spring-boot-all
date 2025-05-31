package com.sam.springdatajpawithuploadanddownload.serviceImpl;

import com.sam.springdatajpawithuploadanddownload.DAO.StudentRepo;
import com.sam.springdatajpawithuploadanddownload.DTO.APIResponse;
import com.sam.springdatajpawithuploadanddownload.DTO.Student;
import com.sam.springdatajpawithuploadanddownload.constants.CommonConstants;
import com.sam.springdatajpawithuploadanddownload.service.FileService;
import com.sam.springdatajpawithuploadanddownload.service.StudentService;
import com.sam.springdatajpawithuploadanddownload.student.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Primary
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private FileService fileService;

    @Override
    public Student saveStudent(Student student) {

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setMobile(student.getMobile());
        studentEntity.setName(student.getName());

        studentRepo.save(studentEntity);

        student.setId(studentEntity.getId());

        return student;
    }

    @Override
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();

        List<StudentEntity> studentEntities = studentRepo.findAll();

        studentEntities.forEach(studentEntity -> {
            Student student = new Student();
            student.setId(studentEntity.getId());
            student.setName(studentEntity.getName());
            student.setMobile(studentEntity.getMobile());
            students.add(student);
        });

        return students;
    }

    @Override
    public void deleteStudent(Integer studentId) {
        studentRepo.deleteById(studentId);
    }

    @Override
    public Student updateStudent(Student student) {
        Optional<StudentEntity> studentEntity = studentRepo.findById(student.getId());

        if (studentEntity.isPresent()) {
            studentEntity.get().setName(student.getName());
            studentEntity.get().setMobile(student.getMobile());
            studentRepo.save(studentEntity.get());
        }

        return student;
    }

    @Override
    public APIResponse uploadStudentDoc(MultipartFile file, Integer studentId) {

        String filePath = fileService.uploadFile(file);

        Optional<StudentEntity> studentEntity = studentRepo.findById(studentId);

        if (studentEntity.isPresent()) {

            if (studentEntity.get().getCertFilePath() != null) {
                fileService.deleteFile(studentEntity.get().getCertFilePath());
            }

            studentEntity.get().setCertFilePath(filePath);
            studentRepo.save(studentEntity.get());
        }

        return new APIResponse(CommonConstants.SUCCESS, 200, "Uploaded Successfully");
    }

    @Override
    public Map<String, Object> downloadStudentDoc(Integer studentId) throws IOException {

        Map<String, Object> response = new HashMap<>();

        Optional<StudentEntity> studentEntity = studentRepo.findById(studentId);

        if (studentEntity.isPresent()) {

            if (studentEntity.get().getCertFilePath() == null) {
                response.put("status", CommonConstants.FAILED);
                response.put("message", "File not uploaded yet. Please upload first.");
                return response;
            }

            InputStream inputStream = fileService.downloadFile(studentEntity.get().getCertFilePath());

            response.put("fileSize", inputStream.available());

            response.put("fileName", new File(studentEntity.get().getCertFilePath()).getName());

            response.put("file", new InputStreamResource(inputStream));

            return response;

        }


        return null;
    }

    @Override
    public List<Student> searchStudents(String name) {
        List<Student> students = new ArrayList<>();

        List<StudentEntity> studentEntities = studentRepo.searchStudents(name);


        studentEntities.forEach(studentEntity -> {
            Student student = new Student();
            student.setId(studentEntity.getId());
            student.setName(studentEntity.getName());
            student.setMobile(studentEntity.getMobile());
            students.add(student);
        });


        return students;
    }


}
