package com.alexsaldan.library;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App utilizando JUnit 5.
 */
public class AppTest {

    /**
     * No JUnit 5, não precisamos mais de construtores complexos 
     * ou de métodos suite(). O @Test resolve tudo.
     */
    @Test
    public void testApp() {
        assertTrue(true);
    }
}

