package com.twobrackets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class OSUtilsTest {

    @BeforeEach
    public void setUp() throws Exception {
        Field field = OSUtils.class.getDeclaredField("osName");
        field.setAccessible(true);
        field.set(null, null);
    }

    @Test
    public void testIsWindows() {
        System.setProperty("os.name", "Windows 10");
        assertTrue(OSUtils.isWindows());
        assertFalse(OSUtils.isLinux());
        assertFalse(OSUtils.isMac());
    }

    @Test
    public void testIsLinux() {
        System.setProperty("os.name", "Linux");
        assertTrue(OSUtils.isLinux());
        assertFalse(OSUtils.isWindows());
        assertFalse(OSUtils.isMac());
    }

    @Test
    public void testIsMac() {
        System.setProperty("os.name", "Mac OS X");
        assertTrue(OSUtils.isMac());
        assertFalse(OSUtils.isWindows());
        assertFalse(OSUtils.isLinux());
    }

    @Test
    public void testGetOsName() {
        System.setProperty("os.name", "Linux");
        Assertions.assertEquals("Linux", OSUtils.getOsName(), "Expected OS to be Linux");
    }
}

