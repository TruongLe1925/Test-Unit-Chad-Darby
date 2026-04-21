package com.luv2code.test;

import com.luv2code.junitdemo.DemoUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnit {
    @Test
    void testEqualsAndNotEquals(){
        DemoUtils demoUtils = new DemoUtils();
        assertEquals(6, demoUtils.add(3, 3),"3+3 should equal 6" );
        assertNotEquals(3, demoUtils.add(4, 5),"4+5 should not equal 3");
    }
    @Test
    void testNullAndNotNull(){
        DemoUtils demoUtils = new DemoUtils();
        String nullValue = null;
        String notNullValue = "Hello";
        assertNull(demoUtils.checkNull(nullValue),"Value should be null");
        assertNotNull(demoUtils.checkNull(notNullValue),"Value should not be null");
    }
}
