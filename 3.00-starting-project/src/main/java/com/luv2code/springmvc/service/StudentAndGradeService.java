package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentAndGradeService {
    @Autowired
    private StudentDAO studentDao;

    public void createStudent(String firstName, String lastName, String email) {
        CollegeStudent student = new CollegeStudent(firstName, lastName, email);
        studentDao.save(student);
    }

    public boolean checknull(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id);
        if (student.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    public List<CollegeStudent> getGradeBook() {
        return (List<CollegeStudent>) studentDao.findAll();
    }
    public void deleteStudent(int id) {
        studentDao.deleteById(id);
    }
    public Iterable<CollegeStudent> getAllStudent() {
        return studentDao.findAll();
    }
}
