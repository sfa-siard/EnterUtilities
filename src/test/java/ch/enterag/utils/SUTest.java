package ch.enterag.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SUTest {

    @Test
    void name() {
        // given

        // when
        String s = "refdgfg";
        assertEquals(s.replace("g", "1"), SU.replace(s,"g","1"));
        // then
    }
}