package ch.enterag.utils.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentsTest {

    private Arguments arguments;

    @BeforeEach
    void setUp() {
        arguments = new Arguments(new String[]{"arg1", "-opt1=value1", "arg2", "-opt2", "-", "-opt3-opt4", "arg3"});
    }

    @Test
    void shouldGetOptions() {
        assertAll(
                () -> assertEquals("value1", arguments.getOption("opt1"), "Value of opt1 should be value1"),
                () -> assertEquals("", arguments.getOption("opt2"), "Value of opt2 should be set explicitly empty"),
                () -> assertNull(arguments.getOption("opt5"), "Non-existent option should return null")
        );
    }

    @Test
    void shouldGetArgumentPosition() {
        assertAll(
                () -> assertEquals("arg1", arguments.getArgumentPosition(0), "First argument should be arg1"),
                () -> assertEquals("arg2", arguments.getArgumentPosition(1), "Second argument should be arg2")
        );
    }

    @Test
    void shouldGetArgumentsNumber() {
        assertEquals(3, arguments.getArgumentsNumber(), "There should be three arguments");
    }

    @Test
    void shouldGetErrors() {
        assertEquals("Empty option encountered!, Option opt3 must be terminated by colon, equals or blank!", arguments.getErrors());
    }
}