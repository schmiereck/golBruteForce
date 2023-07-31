package de.schmiereck.golBruteForce.util;

import static de.schmiereck.golBruteForce.util.MathUtils.invertRuleNr;
import static de.schmiereck.golBruteForce.util.MathUtils.mirrorRuleNr;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MathUtilsTest {

    @Test
    void testInvertRuleNr() {
        assertEquals(0b10110100, invertRuleNr(0b01001011, 2));
        //                      0000011111111111111111111111111
        //
        assertEquals(0b000000111111111111111100100101101L,
          invertRuleNr(0b000000000000000000000011011010010L, 3));
        //                      0000011111111111111111111111111
        assertEquals(0b00000000000000000111100100101101L,
          invertRuleNr(0b00000111111111111000011011010010L, 3));
        //                      0000011111111111111111111111111
        assertEquals(0b000000011111111111111100100101101L,
          invertRuleNr(0b111111100000000000000011011010010L, 3));
    }

    @Test
    void testMirrorRuleNr() {
        assertEquals(0b10110100, mirrorRuleNr(0b00101101, 2));
        //                      0000011111111111111111111111111
        assertEquals(0b00000100000000000000000000000000L,
          mirrorRuleNr(0b00000000000000000000000000000001L, 3));
        //                      0000011111111111111111111111111
        assertEquals(0b00000101100000000000000000000000L,
          mirrorRuleNr(0b00000000000000000000000000001101L, 3));
    }
}