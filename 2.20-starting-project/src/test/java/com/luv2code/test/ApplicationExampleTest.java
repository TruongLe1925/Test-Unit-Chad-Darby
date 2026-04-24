package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes= MvcTestingExampleApplication.class)
public class ApplicationExampleTest {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CollegeStudent collegeStudent;
    @Autowired
    private StudentGrades studentGrades;
    @MockitoBean
    private ApplicationDao applicationDao;
    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    public void beforeEach() {
        collegeStudent.setFirstname("Eric");
        collegeStudent.setLastname("Roby");
        collegeStudent.setEmailAddress("eric.roby@luv2code_school.com");
        collegeStudent.setStudentGrades(studentGrades);
    }
    @Test
    @DisplayName("Test create student")
    public void createStudent() {
        when(applicationDao.addGradeResultsForSingleClass(collegeStudent.getStudentGrades().getMathGradeResults())).thenReturn(100.0);
        assertEquals(100.0, applicationService.addGradeResultsForSingleClass(collegeStudent.getStudentGrades().getMathGradeResults()));
        verify(applicationDao,times(1)).addGradeResultsForSingleClass(collegeStudent.getStudentGrades().getMathGradeResults());
    }
    @Test
    @DisplayName("Test get grade point average")
    public void getGradePointAverage() {
        when(applicationDao.findGradePointAverage(collegeStudent.getStudentGrades().getMathGradeResults())).thenReturn(88.31);
        assertEquals(88.31, applicationService.findGradePointAverage(collegeStudent.getStudentGrades().getMathGradeResults()));
    }
    @DisplayName("Not Null")
    @Test
    public void notNull() {
        when(applicationDao.checkNull(collegeStudent)).thenReturn(collegeStudent);
        assertNotNull(applicationService.checkNull(collegeStudent));
    }
    @DisplayName("Throw Exception")
    @Test
    public void throwException() {
        when(applicationDao.checkNull(null)).thenThrow(new IllegalArgumentException("Student cannot be null"));
        assertThrows(IllegalArgumentException.class, () -> applicationService.checkNull(null));
        assertEquals("Student cannot be null", assertThrows(IllegalArgumentException.class, () -> applicationService.checkNull(null)).getMessage());
    }
}










