package com.luv2code.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FizzBuzzTest {
    @Test
    @DisplayName("Divisible by three")
    @Order(1)
    void testForDivisibleByThree() {
        String expected = "Fizz";
        assertEquals(expected, FizzBuzz.compute(3),"Should return Fizz when number is divisible by three");
    }
    @Test
    @DisplayName("Divisible by five")
    @Order(2)
    void testForDivisibleByFive() {
        String expected = "Buzz";
        assertEquals(expected, FizzBuzz.compute(5),"Should return Buzz when number is divisible by five");
    }
    @Test
    @DisplayName("Divisible by three and five")
    @Order(3)
    void testForDivisibleByThreeAndFive() {
        String expected = "FizzBuzz";
        assertEquals(expected, FizzBuzz.compute(15),"Should return FizzBuzz when number is divisible by three and five");
    }
    @Test
    @DisplayName("Not divisible by three or five")
    @Order(4)
    void testForNotDivisibleByThreeOrFive() {
        String expected = "1";
        assertEquals(expected, FizzBuzz.compute(1),"Should return 1");
    }
    @DisplayName("Testing with CSV file")
    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvFileSource(resources = "/small-test-data.csv")
    @Order(5)
    void testSmallCsvFile(int value,String expected) {
        System.out.println(value + " - " + expected);
        assertEquals(expected, FizzBuzz.compute(value));
    }
    @DisplayName("Testing with CSV file")
    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvFileSource(resources = "/medium-test-data.csv")
    @Order(6)
    void testMediumCsvFile(int value,String expected) {
        System.out.println(value + " - " + expected);
        assertEquals(expected, FizzBuzz.compute(value));
    }
    @DisplayName("Testing with CSV file")
    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvFileSource(resources = "/large-test-data.csv")
    @Order(7)
    void testLargeCsvFile(int value,String expected) {
        System.out.println(value + " - " + expected);
        assertEquals(expected, FizzBuzz.compute(value));
    }
}
