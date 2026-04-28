package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDAO;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("classpath:application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradeBookControllerTest {
    private static MockHttpServletRequest request;
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StudentDAO studentDAO;
    @Mock
    private StudentAndGradeService studentAndGradeService;
    @BeforeAll
    public static void setupRequest() {
        request = new MockHttpServletRequest();
        request.setParameter("firstname", "John");
        request.setParameter("lastname", "Doe");
        request.setParameter("email_address", "john.doe@example.com");
    }
    @BeforeEach
     public void setup() {
        jdbc.execute("INSERT INTO student (firstname, lastname, email_address) VALUES ('John', 'Doe', 'john.doe@example.com')");
        Integer id = jdbc.queryForObject("SELECT id FROM student LIMIT 1", Integer.class);
        System.out.println("ID vừa được chèn là: " + id);
     }
     @AfterEach
     public void cleanup() {
         jdbc.execute("DELETE FROM student");
         jdbc.execute("ALTER TABLE student ALTER COLUMN ID RESTART WITH 1");
     }
     @Test
    public void getStudentsHttpRequest() throws Exception {
         CollegeStudent studentOne = new CollegeStudent("John", "Doe", "john.doe@example.com");
         CollegeStudent studentTwo = new CollegeStudent("Jane", "Doe", "jane.doe@example.com");
         List<CollegeStudent> students = new ArrayList<>(List.of(studentOne, studentTwo));
         when(studentAndGradeService.getGradeBook()).thenReturn(students);
         assertIterableEquals(students, studentAndGradeService.getGradeBook());
         MvcResult mvcResult = this.mockMvc.perform(get("/"))
                 .andExpect(status().isOk()).andReturn();
         ModelAndView mav = mvcResult.getModelAndView();
         ModelAndViewAssert.assertViewName(mav, "index");
    }
    @Test
    public void createStudentHttpRequest() throws Exception {
        CollegeStudent studentOne = new CollegeStudent("John", "Doe", "john.doe@example.com");
        List<CollegeStudent> students = new ArrayList<>(List.of(studentOne));
        when(studentAndGradeService.getGradeBook()).thenReturn(students);
        assertIterableEquals(students, studentAndGradeService.getGradeBook());
        MvcResult mvcResult = this.mockMvc.perform(post("/").
                contentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED).
                param("firstname", "John").
                param("lastname", "Doe").
                param("email_address", "john.doe@example.com")).
                andExpect(status().isOk()).
                andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "index");
    }
    @Test
    public void deleteStudentHttpRequest() throws Exception {
        assertTrue(studentDAO.findById(1).isPresent(), "Student should be present");
        MvcResult mvcResult = this.mockMvc.perform(get("/delete/student/{id}",1))
                .andExpect(status().isOk()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "index");
        assertFalse(studentDAO.findById(1).isPresent(), "Student should be deleted");
    }
    @Test
    public void error() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/delete/student/{id}",0))
                .andExpect(status().isOk()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "error");
    }
}
