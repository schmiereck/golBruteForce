package de.schmiereck.golBruteForce;

import static de.schmiereck.golBruteForce.util.PrintUtils.toBinary;
import static org.junit.jupiter.api.Assertions.*;

class RuleServiceTest {

    @org.junit.jupiter.api.Test
    void test1() {
        final long v1 = 0b00000000000000000000000000000111;
        //final long v2 = 0b00000000000000000000000000000111;
        final long v2 = 0b00000000000000000000000000001000;
        final long v = v1 % v2;

        System.out.printf("%s %,d v1\n", toBinary(v1, 32), v1);
        System.out.printf("%s %,d v2\n", toBinary(v2, 32), v2);
        System.out.printf("%s %,d v\n", toBinary(v, 32), v);
    }

    @org.junit.jupiter.api.Test
    void test2() {
        final long v1 = 0b00001000000000000000000000000111;
        //final long v2 = 0b00000000000000000000000000000111;
        final long v2 = 0b00000000000000000000000000001000;
        final long v = v1 % v2;

        System.out.printf("%s %,d v1\n", toBinary(v1, 32), v1);
        System.out.printf("%s %,d v2\n", toBinary(v2, 32), v2);
        System.out.printf("%s %,d v\n", toBinary(v, 32), v);
    }

    @org.junit.jupiter.api.Test
    void test3() {
        final long v1 = 0b00001000000000000000000000101010;
        final long v2 = 0b00001000000000000000000000000000;
        final long v = v1 % v2;

        System.out.printf("%s %,d v1\n", toBinary(v1, 32), v1);
        System.out.printf("%s %,d v2\n", toBinary(v2, 32), v2);
        System.out.printf("%s %,d v\n", toBinary(v, 32), v);
    }

    @org.junit.jupiter.api.Test
    void createRule2() {
        final int stateCount = 3;
        final long ruleNr = 0b00000000000000000000000000101010;

        final long inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, inputCombinationRuleCount);
        final long level0RuleNr = RuleService.calcLevel0RuleNr(ruleNr, baseRuleCount);
        final long level1RuleNr = RuleService.calcLevel1RuleNr(ruleNr, baseRuleCount);

        System.out.printf("%s %,d ruleNr\n", toBinary(ruleNr, 32), ruleNr);
        System.out.printf("%s %,d inputCombinationRuleCount\n", toBinary(inputCombinationRuleCount, 32), inputCombinationRuleCount);
        System.out.printf("%s %,d baseRuleCount\n", toBinary(baseRuleCount, 32), baseRuleCount);
        System.out.printf("%s %,d baseRuleCount - 1\n", toBinary(baseRuleCount - 1, 32), baseRuleCount - 1);
        System.out.printf("%s %,d level0RuleNr\n", toBinary(level0RuleNr, 32), level0RuleNr);
        System.out.printf("%s %,d level1RuleNr\n", toBinary(level1RuleNr, 32), level1RuleNr);

        assertEquals(0b00000000000000000000000000101010, level0RuleNr);
        assertEquals(0b00000000000000000000000000000000, level1RuleNr);
    }

    @org.junit.jupiter.api.Test
    void createRule1_0() {
        final int stateCount = 3;
        final long ruleNr = 0b00001000000000000000000000000000;

        final long inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, inputCombinationRuleCount);
        final long level0RuleNr = RuleService.calcLevel0RuleNr(ruleNr, baseRuleCount);
        final long level1RuleNr = RuleService.calcLevel1RuleNr(ruleNr, baseRuleCount);

        System.out.printf("%s %,d ruleNr\n", toBinary(ruleNr, 32), ruleNr);
        System.out.printf("%s %,d inputCombinationRuleCount\n", toBinary(inputCombinationRuleCount, 32), inputCombinationRuleCount);
        System.out.printf("%s %,d ruleCount\n", toBinary(baseRuleCount, 32), baseRuleCount);
        System.out.printf("%s %,d level0RuleNr\n", toBinary(level0RuleNr, 32), level0RuleNr);
        System.out.printf("%s %,d level1RuleNr\n", toBinary(level1RuleNr, 32), level1RuleNr);

        assertEquals(0b00000000000000000000000000000000, level0RuleNr);
        assertEquals(0b00000000000000000000000000000001, level1RuleNr);
    }

    @org.junit.jupiter.api.Test
    void createRule1_1() {
        final int stateCount = 3;
        final long ruleNr = 0b00001000000000000000000000000001;

        final long inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, inputCombinationRuleCount);
        final long level0RuleNr = RuleService.calcLevel0RuleNr(ruleNr, baseRuleCount);
        final long level1RuleNr = RuleService.calcLevel1RuleNr(ruleNr, baseRuleCount);

        System.out.printf("%s %,d ruleNr\n", toBinary(ruleNr, 32), ruleNr);
        System.out.printf("%s %,d inputCombinationRuleCount\n", toBinary(inputCombinationRuleCount, 32), inputCombinationRuleCount);
        System.out.printf("%s %,d baseRuleCount\n", toBinary(baseRuleCount, 32), baseRuleCount);
        System.out.printf("%s %,d level0RuleNr\n", toBinary(level0RuleNr, 32), level0RuleNr);
        System.out.printf("%s %,d level1RuleNr\n", toBinary(level1RuleNr, 32), level1RuleNr);

        assertEquals(0b00000000000000000000000000000001, level0RuleNr);
        assertEquals(0b00000000000000000000000000000001, level1RuleNr);
    }

    @org.junit.jupiter.api.Test
    void createRule1_x() {
        final int stateCount = 3;
        final long ruleNr = 0b00001000000000000000000000101010;

        final long inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, inputCombinationRuleCount);
        final long level0RuleNr = RuleService.calcLevel0RuleNr(ruleNr, baseRuleCount);
        final long level1RuleNr = RuleService.calcLevel1RuleNr(ruleNr, baseRuleCount);

        System.out.printf("%s %,d ruleNr\n", toBinary(ruleNr, 32), ruleNr);
        System.out.printf("%s %,d inputCombinationRuleCount\n", toBinary(inputCombinationRuleCount, 32), inputCombinationRuleCount);
        System.out.printf("%s %,d baseRuleCount\n", toBinary(baseRuleCount, 32), baseRuleCount);
        System.out.printf("%s %,d baseRuleCount - 1\n", toBinary(baseRuleCount - 1, 32), baseRuleCount - 1);
        System.out.printf("%s %,d level0RuleNr\n", toBinary(level0RuleNr, 32), level0RuleNr);
        System.out.printf("%s %,d level1RuleNr\n", toBinary(level1RuleNr, 32), level1RuleNr);

        assertEquals(0b00000000000000000000000000101010, level0RuleNr);
        assertEquals(0b00000000000000000000000000000001, level1RuleNr);
    }
}