package com.sam.springdatajpawithuploadanddownload.DAO;

import com.sam.springdatajpawithuploadanddownload.student.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepo extends JpaRepository<StudentEntity,Integer> {
    @Query("SELECT s FROM StudentEntity s WHERE s.name LIKE  %:name%")
    List<StudentEntity> searchStudents(@Param(value = "name") String name);

}
