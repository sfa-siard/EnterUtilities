package ch.enterag.utils.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentsTest {

    private Arguments arguments;

    @BeforeEach
    void setUp() {
        arguments = new Arguments(new String[]{"arg1", "-opt1=value1", "arg2", "-opt2", "-", "-opt3-opt4"});
    }

    @Test
    void shouldGetOptions() {
        assertAll(
                () -> assertEquals("value1", arguments.getOptions("opt1"), "Value of opt1 should be value1"),
                () -> assertEquals("", arguments.getOptions("opt2"), "Value of opt2 should be set explicitly empty"),
                () -> assertNull(arguments.getOptions("opt5"), "Non-existent option should return null")
        );
    }

    @Test
    void shouldGetArgumentPosition() {
        assertEquals("arg1", arguments.getArgumentPosition(0), "First argument should be arg1");
    }

    @Test
    void shouldGetArgumentsNumber() {
        assertEquals(2, arguments.getArgumentsNumber(), "There should be two arguments");
    }

}