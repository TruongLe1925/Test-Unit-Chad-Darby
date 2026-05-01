package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.models.ScienceGrade;
import com.luv2code.springmvc.repository.HistoryGradeDAO;
import com.luv2code.springmvc.repository.MathGradeDAO;
import com.luv2code.springmvc.repository.ScienceGradeDAO;
import com.luv2code.springmvc.repository.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentAndGradeService {
    @Autowired
    private StudentDAO studentDao;
    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;
    @Autowired
    @Qualifier("scienceGrades")
    private ScienceGrade scienceGrade;
    @Autowired
    @Qualifier("historyGrades")
    private HistoryGrade historyGrade;
    @Autowired
    private HistoryGradeDAO historyGradeDao;
    @Autowired
    private ScienceGradeDAO scienceGradeDao;
    @Autowired
    private MathGradeDAO mathGradeDao;
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
    public boolean createGrade(double grade, int id, String subject) {
        if(!checknull(id)){
            return false;
        }
        if (grade >= 0 && grade <= 100) {
             if(subject.equals("math")){
                 mathGrade.setGrade(grade);
                 mathGrade.setStudentId(id);
                 mathGrade.setId(0);
                 mathGradeDao.save(mathGrade);
                 return true;
             }
            if(subject.equals("science")){
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(id);
                scienceGrade.setId(0);
                scienceGradeDao.save(scienceGrade);
                return true;
            }
            if(subject.equals("history")){
                historyGrade.setGrade(grade);
                historyGrade.setStudentId(id);
                historyGrade.setId(0);
                historyGradeDao.save(historyGrade);
                return true;
            }
        }
        return false;
    }
}
