package com.luv2code.test;

import com.luv2code.junitdemo.DemoUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUnit {
    DemoUtils demoUtils;
    @BeforeEach
    void setupBeforeEach(){
        demoUtils = new DemoUtils();
    }
//    @AfterEach
//    void tearDownAfterEach(){
//        System.out.println("tearDownAfterEach\n");
//    }
//    @BeforeAll
//    static void setupBeforeAll(){
//        System.out.println("setupBeforeAll\n");
//    }
//    @AfterAll
//    static void tearDownAfterAll(){
//        System.out.println("tearDownAfterAll");
//    }
    @Test
    @DisplayName("Test Equals and Not Equals")
    @Order(1)
    @Disabled("Disabled until further notice")
    void testEqualsAndNotEquals(){
        assertEquals(6, demoUtils.add(3, 3),"3+3 should equal 6" );
        assertNotEquals(3, demoUtils.add(4, 5),"4+5 should not equal 3");
    }
    @Test
    @DisplayName("Test Null and Not Null")
    void testNullAndNotNull(){
        String nullValue = null;
        String notNullValue = "Hello";
        assertNull(demoUtils.checkNull(nullValue),"Value should be null");
        assertNotNull(demoUtils.checkNull(notNullValue),"Value should not be null");
    }
    @Test
    @DisplayName("Same And Not Same")
    void testSameAndNotSame(){
        String str = "luv2code";
        assertSame(demoUtils.getAcademy(),demoUtils.getAcademyDuplicate(),"Values should be the same");
        assertNotSame(str, demoUtils.getAcademy(),"Values should not be the same");
    }
    @Test
    @DisplayName("True And False")
    void testTrueFalse(){
        int gradeOne = 10;
        int gradeTwo = 20;
        assertTrue(demoUtils.isGreater(gradeTwo, gradeOne),"gradeTwo should be greater than gradeOne");
        assertFalse(demoUtils.isGreater(gradeOne, gradeTwo),"gradeOne should not be greater than gradeTwo");
    }
    @Test
    @DisplayName("Array Equals")
    void testArrayEquals()
    {
        String[] stringArray = {"A","B","C"};
        assertArrayEquals(stringArray, demoUtils.getFirstThreeLettersOfAlphabet(),"Arrays should be equal");
    }
    @Test
    @DisplayName("Iterable Equals")
    void testIterableEquals(){
        List<String> expected = java.util.List.of("luv","2","code");
        assertIterableEquals(expected, demoUtils.getAcademyInList(),"Iterable should be equal");
    }
    @Test
    @DisplayName("Line Match")
    void testLineMatch(){
        List<String> expected = java.util.List.of("luv","2","code");
       assertLinesMatch(expected, demoUtils.getAcademyInList(),"Objects should be equal");
    }
    @Test
    @DisplayName("Throws Exception")
    void testThrowsException(){
        assertThrows(Exception.class, () -> demoUtils.throwException(-1),"Should throw exception");
    }
    @Test
    @DisplayName("check time out")
    void testCheckTimeout() throws InterruptedException {
        assertTimeoutPreemptively(Duration.ofSeconds(3), () -> demoUtils.checkTimeout(),"Should timeout");
    }
    @Test
    @Order(2)
    @DisplayName("Test Multiply")
    void testMultiply(){
        assertEquals(10, demoUtils.multiply(5, 2),"5*2 should equal 10");
    }
}
