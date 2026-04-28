package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
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

import java.util.ArrayList;
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
    @BeforeEach
    public void setup() {
//        jdbc.execute("INSERT INTO student (firstname, lastname, email_address) VALUES ('John', 'Doe', 'john.doe@example.com')");
//        Integer id = jdbc.queryForObject("SELECT id FROM student LIMIT 1", Integer.class);
//        System.out.println("ID vừa được chèn là: " + id);
        studentAndGradeService.createStudent("john", "doe", "john.doe@example.com");
    }

    @AfterEach
    public void cleanup() {
        jdbc.execute("DELETE FROM student");
        jdbc.execute("ALTER TABLE student ALTER COLUMN ID RESTART WITH 1");
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
}
