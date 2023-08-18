package de.schmiereck.golBruteForce;

import static de.schmiereck.golBruteForce.CalcService.calcInitRunFilter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CalcServiceTest {
    @Test
    public void GIVEN_initPos_1_THEN_expectedRuleNrArr() {
        // Main.calcSimpleForInit(false, 1, 1, false);
        final int size = 32 + 1;

        final int historyCount = 16 + 1;

        final int initSize = 1;//7;//5;//3;
        final int initPos = 0b00001; // 1

        final int cellCount = 3;
        final int stateCount = 2;//3;

        final long[] expectedRuleNrArr = {
        2,
        4,
        6,
        10,
        12,
        14,
        20,
        24,
        34,
        36,
        38,
        42,
        44,
        46,
        66,
        74,
                -1
        };

        runRulesTest(size, historyCount, initSize, initPos, cellCount, stateCount, expectedRuleNrArr);
    }

    @Test
    public void GIVEN_initPos_3_THEN_expectedRuleNrArr() {
        // Main.calcSimpleForInit(false, 2, 3, false);
        final int size = 32 + 1;

        final int historyCount = 16 + 1;

        final int initSize = 2;//7;//5;//3;
        final int initPos = 0b00011; // 3

        final int cellCount = 3;
        final int stateCount = 2;//3;

        final long[] expectedRuleNrArr = {
        2 ,
        6 ,
        10,
        12,
        14,
        20,
        24,
        34,
        38,
        42,
        44,
        46,
        66,
        74,
                -1
        };

        runRulesTest(size, historyCount, initSize, initPos, cellCount, stateCount, expectedRuleNrArr);
    }

    @Test
    public void GIVEN_initPos_5_THEN_expectedRuleNrArr() {
        // Main.calcSimpleForInit(false, 3, 5, false);
        final int size = 32 + 1;

        final int historyCount = 16 + 1;

        final int initSize = 3;//7;//5;//3;
        final int initPos = 0b00101; // 5

        final int cellCount = 3;
        final int stateCount = 2;//3;

        final long[] expectedRuleNrArr = {
        2 ,
        4 ,
        6 ,
        10,
        12,
        14,
        20,
        24,
        34,
        38,
        42,
        44,
        46,
        66,
        74,
                -1
        };

        runRulesTest(size, historyCount, initSize, initPos, cellCount, stateCount, expectedRuleNrArr);
    }

    @Test
    public void GIVEN_initPos_7_THEN_expectedRuleNrArr() {
        // Main.calcSimpleForInit(false, 3, 5, false);
        final int size = 32 + 1;

        final int historyCount = 16 + 1;

        final int initSize = 3;//7;//5;//3;
        final int initPos = 0b00111; // 7

        final int cellCount = 3;
        final int stateCount = 2;//3;

        final long[] expectedRuleNrArr = {
        2 ,
        6 ,
        10,
        12,
        14,
        20,
        24,
        34,
        38,
        42,
        44,
        46,
        66,
        74,
                -1
        };

        runRulesTest(size, historyCount, initSize, initPos, cellCount, stateCount, expectedRuleNrArr);
    }

    @Test
    public void GIVEN_initPos_13_THEN_expectedRuleNrArr() {
        // Main.calcSimpleForInit(false, 3, 5, false);
        final int size = 32 + 1;

        final int historyCount = 16 + 1;

        final int initSize = 4;//7;//5;//3;
        final int initPos = 0b01101; // 13 (8421)

        final int cellCount = 3;
        final int stateCount = 2;//3;

        final long[] expectedRuleNrArr = {
        2 ,
        4 ,
        6 ,
        10,
        12,
        14,
        20,
        24,
        34,
        38,
        42,
        44,
        46,
        66,
        74,
                -1
        };

        runRulesTest(size, historyCount, initSize, initPos, cellCount, stateCount, expectedRuleNrArr);
    }

    private static void runRulesTest(final int size, final int historyCount, final int initSize, final int initPos, final int cellCount, final int stateCount, final long[] expectedRuleNrArr) {
        final int inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, inputCombinationRuleCount);

        int expectedRuleNrPos = 0;

        for (long ruleNr = 0; ruleNr < baseRuleCount; ruleNr++) {
            final CalcService.RuleCheck ruleCheck = CalcService.checkRule(ruleNr, stateCount);

            if (ruleCheck == CalcService.RuleCheck.Run) {
                final Rule rule = RuleService.createRule(stateCount, ruleNr, cellCount);

                final World world = new World(size, historyCount);

                final FilterResult filterResult = calcInitRunFilter(rule, world, initSize, initPos);

                if (CalcService.checkFilterResult(filterResult)) {
                    assertEquals(expectedRuleNrArr[expectedRuleNrPos], rule.ruleNr);
                    expectedRuleNrPos++;
                } else {
                    assertNotEquals(expectedRuleNrArr[expectedRuleNrPos], rule.ruleNr);
                }
            }
        }
    }
}
