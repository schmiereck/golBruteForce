package de.schmiereck.golBruteForce;

import static de.schmiereck.golBruteForce.util.PrintUtils.toBinaryStr;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RuleServiceTest {

    @Test
    void test1() {
        final long v1 = 0b00000000000000000000000000000111;
        //final long v2 = 0b00000000000000000000000000000111;
        final long v2 = 0b00000000000000000000000000001000;
        final long v = v1 % v2;

        System.out.printf("%s %,d v1\n", toBinaryStr(v1, 32), v1);
        System.out.printf("%s %,d v2\n", toBinaryStr(v2, 32), v2);
        System.out.printf("%s %,d v\n", toBinaryStr(v, 32), v);
    }

    @Test
    void test2() {
        final long v1 = 0b00001000000000000000000000000111;
        //final long v2 = 0b00000000000000000000000000000111;
        final long v2 = 0b00000000000000000000000000001000;
        final long v = v1 % v2;

        System.out.printf("%s %,d v1\n", toBinaryStr(v1, 32), v1);
        System.out.printf("%s %,d v2\n", toBinaryStr(v2, 32), v2);
        System.out.printf("%s %,d v\n", toBinaryStr(v, 32), v);
    }

    @Test
    void test3() {
        final long v1 = 0b00001000000000000000000000101010;
        final long v2 = 0b00001000000000000000000000000000;
        final long v = v1 % v2;

        System.out.printf("%s %,d v1\n", toBinaryStr(v1, 32), v1);
        System.out.printf("%s %,d v2\n", toBinaryStr(v2, 32), v2);
        System.out.printf("%s %,d v\n", toBinaryStr(v, 32), v);
    }

    @Test
    void createRule2() {
        final int stateCount = 3;
        final long ruleNr = 0b00000000000000000000000000101010;

        final long inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, inputCombinationRuleCount);
        final long level0RuleNr = RuleService.calcLevel0RuleNr(ruleNr, baseRuleCount);
        final long level1RuleNr = RuleService.calcLevel1RuleNr(ruleNr, baseRuleCount);

        System.out.printf("%s %,d ruleNr\n", toBinaryStr(ruleNr, 32), ruleNr);
        System.out.printf("%s %,d inputCombinationRuleCount\n", toBinaryStr(inputCombinationRuleCount, 32), inputCombinationRuleCount);
        System.out.printf("%s %,d baseRuleCount\n", toBinaryStr(baseRuleCount, 32), baseRuleCount);
        System.out.printf("%s %,d baseRuleCount - 1\n", toBinaryStr(baseRuleCount - 1, 32), baseRuleCount - 1);
        System.out.printf("%s %,d level0RuleNr\n", toBinaryStr(level0RuleNr, 32), level0RuleNr);
        System.out.printf("%s %,d level1RuleNr\n", toBinaryStr(level1RuleNr, 32), level1RuleNr);

        assertEquals(0b00000000000000000000000000101010, level0RuleNr);
        assertEquals(0b00000000000000000000000000000000, level1RuleNr);
    }

    @Test
    void createRule1_0() {
        final int stateCount = 3;
        final long ruleNr = 0b00001000000000000000000000000000;

        final long inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, inputCombinationRuleCount);
        final long level0RuleNr = RuleService.calcLevel0RuleNr(ruleNr, baseRuleCount);
        final long level1RuleNr = RuleService.calcLevel1RuleNr(ruleNr, baseRuleCount);

        System.out.printf("%s %,d ruleNr\n", toBinaryStr(ruleNr, 32), ruleNr);
        System.out.printf("%s %,d inputCombinationRuleCount\n", toBinaryStr(inputCombinationRuleCount, 32), inputCombinationRuleCount);
        System.out.printf("%s %,d ruleCount\n", toBinaryStr(baseRuleCount, 32), baseRuleCount);
        System.out.printf("%s %,d level0RuleNr\n", toBinaryStr(level0RuleNr, 32), level0RuleNr);
        System.out.printf("%s %,d level1RuleNr\n", toBinaryStr(level1RuleNr, 32), level1RuleNr);

        assertEquals(0b00000000000000000000000000000000, level0RuleNr);
        assertEquals(0b00000000000000000000000000000001, level1RuleNr);
    }

    @Test
    void createRule1_1() {
        final int stateCount = 3;
        final long ruleNr = 0b00001000000000000000000000000001;

        final long inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, inputCombinationRuleCount);
        final long level0RuleNr = RuleService.calcLevel0RuleNr(ruleNr, baseRuleCount);
        final long level1RuleNr = RuleService.calcLevel1RuleNr(ruleNr, baseRuleCount);

        System.out.printf("%s %,d ruleNr\n", toBinaryStr(ruleNr, 32), ruleNr);
        System.out.printf("%s %,d inputCombinationRuleCount\n", toBinaryStr(inputCombinationRuleCount, 32), inputCombinationRuleCount);
        System.out.printf("%s %,d baseRuleCount\n", toBinaryStr(baseRuleCount, 32), baseRuleCount);
        System.out.printf("%s %,d level0RuleNr\n", toBinaryStr(level0RuleNr, 32), level0RuleNr);
        System.out.printf("%s %,d level1RuleNr\n", toBinaryStr(level1RuleNr, 32), level1RuleNr);

        assertEquals(0b00000000000000000000000000000001, level0RuleNr);
        assertEquals(0b00000000000000000000000000000001, level1RuleNr);
    }

    @Test
    void createRule1_x() {
        final int stateCount = 3;
        final long ruleNr = 0b00001000000000000000000000101010;

        final long inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, inputCombinationRuleCount);
        final long level0RuleNr = RuleService.calcLevel0RuleNr(ruleNr, baseRuleCount);
        final long level1RuleNr = RuleService.calcLevel1RuleNr(ruleNr, baseRuleCount);

        System.out.printf("%s %,d ruleNr\n", toBinaryStr(ruleNr, 32), ruleNr);
        System.out.printf("%s %,d inputCombinationRuleCount\n", toBinaryStr(inputCombinationRuleCount, 32), inputCombinationRuleCount);
        System.out.printf("%s %,d baseRuleCount\n", toBinaryStr(baseRuleCount, 32), baseRuleCount);
        System.out.printf("%s %,d baseRuleCount - 1\n", toBinaryStr(baseRuleCount - 1, 32), baseRuleCount - 1);
        System.out.printf("%s %,d level0RuleNr\n", toBinaryStr(level0RuleNr, 32), level0RuleNr);
        System.out.printf("%s %,d level1RuleNr\n", toBinaryStr(level1RuleNr, 32), level1RuleNr);

        assertEquals(0b00000000000000000000000000101010, level0RuleNr);
        assertEquals(0b00000000000000000000000000000001, level1RuleNr);
    }

    @Test
    void createRule1_max_3() {
        final int stateCount = 3;
        final long ruleNr = 0b111111111111111111111111111_111111111111111111111111111L;

        final long inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, inputCombinationRuleCount);
        final long level0RuleNr = RuleService.calcLevel0RuleNr(ruleNr, baseRuleCount);
        final long level1RuleNr = RuleService.calcLevel1RuleNr(ruleNr, baseRuleCount);

        System.out.printf("%s %,d ruleNr\n", toBinaryStr(ruleNr, 64), ruleNr);
        System.out.printf("%s %,d inputCombinationRuleCount\n", toBinaryStr(inputCombinationRuleCount, 64), inputCombinationRuleCount);
        System.out.printf("%s %,d baseRuleCount\n", toBinaryStr(baseRuleCount, 64), baseRuleCount);
        System.out.printf("%s %,d baseRuleCount - 1\n", toBinaryStr(baseRuleCount - 1, 64), baseRuleCount - 1);
        System.out.printf("%s %,d level0RuleNr\n", toBinaryStr(level0RuleNr, 64), level0RuleNr);
        System.out.printf("%s %,d level1RuleNr\n", toBinaryStr(level1RuleNr, 64), level1RuleNr);

        assertEquals(0b00000111111111111111111111111111L, level0RuleNr);
        assertEquals(0b00000111111111111111111111111111L, level1RuleNr);
    }

    @Test
    void calcInputCombinationBitCount() {
        assertEquals(1, RuleService.calcInputCombinationBitCount(1));
        assertEquals(8, RuleService.calcInputCombinationBitCount(2));
        assertEquals(27, RuleService.calcInputCombinationBitCount(3));
        assertEquals(64, RuleService.calcInputCombinationBitCount(4));
    }

    @Test
    void calcInputCombinationRuleCount() {
        //assertEquals(1, RuleService.calcInputCombinationRuleCount(1));
        assertEquals(256, RuleService.calcInputCombinationRuleCount(2));
        assertEquals(32_768, RuleService.calcInputCombinationRuleCount(3));

        //assertEquals(1, RuleService.calcInputCombinationRuleCount2(1));
        assertEquals(256, RuleService.calcInputCombinationRuleCount2(2));
        assertEquals(32_768, RuleService.calcInputCombinationRuleCount2(3));
        assertEquals(13_4217_728, RuleService.calcInputCombinationRuleCount2(3));
    }
}