package de.schmiereck.golBruteForce.util;

import static de.schmiereck.golBruteForce.util.MathUtils.checkPreviousRule;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MathUtils_WHEN_checkPreviousRule_is_called {
    @Test
    void GIVEN_stateCount_2_THEN_stateCount_1_ruleNumbers_found() {
        assertTrue(checkPreviousRule(0L, 2));
    }
}
