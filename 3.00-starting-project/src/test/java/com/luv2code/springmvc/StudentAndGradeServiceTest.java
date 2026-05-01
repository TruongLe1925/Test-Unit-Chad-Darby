package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.models.ScienceGrade;
import com.luv2code.springmvc.repository.HistoryGradeDAO;
import com.luv2code.springmvc.repository.MathGradeDAO;
import com.luv2code.springmvc.repository.ScienceGradeDAO;
import com.luv2code.springmvc.repository.StudentDAO;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import javax.swing.plaf.SplitPaneUI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {
    @Autowired
    private StudentAndGradeService studentAndGradeService;
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private StudentDAO studentDao;
    @Autowired
    private MathGradeDAO mathGradeDAO;
    @Autowired
    private ScienceGradeDAO scienceGradeDAO;
    @Autowired
    private HistoryGradeDAO historyGradeDAO;
    @BeforeEach
    public void setup() {
//        jdbc.execute("INSERT INTO student (firstname, lastname, email_address) VALUES ('John', 'Doe', 'john.doe@example.com')");
//        Integer id = jdbc.queryForObject("SELECT id FROM student LIMIT 1", Integer.class);
//        System.out.println("ID vừa được chèn là: " + id);
        studentAndGradeService.createStudent("john", "doe", "john.doe@example.com");
        jdbc.execute("INSERT INTO math_grade (student_id,grade) VALUES (1,100.00)");
        jdbc.execute("INSERT INTO science_grade (student_id,grade) VALUES (1,100.00)");
        jdbc.execute("INSERT INTO history_grade (student_id,grade) VALUES (1,100.00)");
    }

    @AfterEach
    public void cleanup() {
        jdbc.execute("DELETE FROM student");
        jdbc.execute("DELETE FROM math_grade");
        jdbc.execute("DELETE FROM science_grade");
        jdbc.execute("DELETE FROM history_grade");

        jdbc.execute("ALTER TABLE student ALTER COLUMN ID RESTART WITH 1");
        jdbc.execute("ALTER TABLE math_grade ALTER COLUMN ID RESTART WITH 1");
        jdbc.execute("ALTER TABLE science_grade ALTER COLUMN ID RESTART WITH 1");
        jdbc.execute("ALTER TABLE history_grade ALTER COLUMN ID RESTART WITH 1");

    }
    @Test
    public void createStudentService() {

        CollegeStudent student = studentDao.findByEmailAddress("john.doe@example.com");
        assertEquals("john.doe@example.com", student.getEmailAddress());
    }
    @Test
    public void isStudentNull() {
        assertTrue(studentAndGradeService.checknull(1));
        assertFalse(studentAndGradeService.checknull(0));
    }
    @Test
    public void deleteStudent() {
        Optional<CollegeStudent> student = studentDao.findById(1);
        assertTrue(student.isPresent(),"Student should be present");
        studentAndGradeService.deleteStudent(1);
        assertFalse(studentAndGradeService.checknull(1));
    }
    @Test
    @Sql("classpath:insertIntoStudents.sql")
    public void getStudent() {
        Iterable<CollegeStudent> student = studentAndGradeService.getAllStudent();
        List<CollegeStudent> collegeStudents = new ArrayList<>();
        for(CollegeStudent students : student ){
            collegeStudents.add(students);
        }
        assertEquals(5, collegeStudents.size());
    }
    @Test
    public void createGradeService(){
        //create the grade
        assertTrue(studentAndGradeService.createGrade(88.00,1,"math"));
        assertTrue(studentAndGradeService.createGrade(88.00,1,"science"));
        assertTrue(studentAndGradeService.createGrade(88.00,1,"history"));
        Iterable<MathGrade> mathGrades = mathGradeDAO.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradeDAO.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradeDAO.findGradeByStudentId(1);
        assertTrue(((Collection<MathGrade>) mathGrades).size() == 2,"student has math grades");
        assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 2,"student has science grades");
        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 2,"student has history grades");

    }
    @Test
    public void createGradeServiceReturnFalse(){
        //create the grade
        assertFalse(studentAndGradeService.createGrade(105,1,"math"));
        assertFalse(studentAndGradeService.createGrade(-1,1,"math"));
        assertFalse(studentAndGradeService.createGrade(60.50,2,"math"));
        assertFalse(studentAndGradeService.createGrade(60.50,2,"literature"));
    }
}
