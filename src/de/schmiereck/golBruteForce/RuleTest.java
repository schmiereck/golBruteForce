package de.schmiereck.golBruteForce;

import static de.schmiereck.golBruteForce.RuleService.createRule;
import static de.schmiereck.golBruteForce.RuleService.createRuleFor2States;
import static de.schmiereck.golBruteForce.util.PrintUtils.toBinaryStr;

public class RuleTest {
    public static void main(String[] args) {
        final int cellCount = 3;
        /*
        final int stateCount = 2;//3;
        final int ruleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        for (int ruleNr = 0; ruleNr < ruleCount; ruleNr++) {
            //final Rule rule = RuleService.createRule(stateCount, ruleNr, cellCount);
            final Rule rule = createRuleFor2States(stateCount, ruleNr, cellCount);
            showRule(rule);
        }
        */
        {
            final int stateCount = 2;
            showRule(createRuleFor2States(stateCount, 0b00000000000000000000000000000000, cellCount));
            showRule(createRuleFor2States(stateCount, 0b00000000000000000000000000000001, cellCount));
            showRule(createRuleFor2States(stateCount, 0b00000000000000000000000011111111, cellCount));
        }
        {
            final int stateCount = 3;
            showRule(createRule(stateCount, 0b000000000000000000000000000_000000000000000000000000000L, cellCount));
            showRule(createRule(stateCount, 0b000000000000000000000000000_000000000000000000000000001L, cellCount));
            showRule(createRule(stateCount, 0b000000000000000000000000000_000000000000000000000101010L, cellCount));
            showRule(createRule(stateCount, 0b000000000000000000000000000_111111111111111111111111111L, cellCount));
            showRule(createRule(stateCount, 0b000000000000000000000000001_000000000000000000000000000L, cellCount));
            showRule(createRule(stateCount, 0b000000000000000000000000001_000000000000000000000010101L, cellCount));
            showRule(createRule(stateCount, 0b000000000000000000000000001_000000000000000000000101010L, cellCount));
            showRule(createRule(stateCount, 0b000000000000000000000000001_111111111111111111111111111L, cellCount));
            showRule(createRule(stateCount, 0b010101010101010101010101010_111111111111111111111111111L, cellCount));
            showRule(createRule(stateCount, 0b101010101010101010101010101_111111111111111111111111111L, cellCount));
            showRule(createRule(stateCount, 0b101010101010101010101010101_101010101010101010101010101L, cellCount));
            showRule(createRule(stateCount, 0b101010101010101010101010101_010101010101010101010101010L, cellCount));
            showRule(createRule(stateCount, 0b010101010101010101010101010_010101010101010101010101010L, cellCount));
            showRule(createRule(stateCount, 0b111111111111111111111111111_111111111111111111111111111L, cellCount));
        }
    }

    private static void showRule(final Rule rule) {
        System.out.println("=================================================================================");
        System.out.printf("Rule-Nr: %,d [%,d + %,d], stateCount:%2d\n", rule.ruleNr, rule.level0RuleNr, rule.level1RuleNr, rule.stateCount);
        System.out.printf("         %s\n", toBinaryStr(rule.level0RuleNr, 32));
        System.out.printf("         %s\n", toBinaryStr(rule.level1RuleNr, 32));

        System.out.printf("%s\n", toBinaryStr(rule.ruleNr, 64));
        //final long inputCombinationRuleCount = (((1 << (rule.stateCount * rule.stateCount)) << rule.stateCount) << rule.stateCount);
        final long inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(rule.stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(rule.stateCount, inputCombinationRuleCount);
        final long max2 = 0b00000111111111111111111111111111; // stateCount: 3
        //final long max2 = 0b00000000000000000000000011111111; // stateCount: 2
        final long level0RuleNr = RuleService.calcLevel0RuleNr(rule.ruleNr, baseRuleCount);

        //final long level0RuleNr = rule.ruleNr % max;
        System.out.printf("%s (inputCombinationRuleCount:%,d) (baseRuleCount:%,d) (max2:%,d)\n", toBinaryStr(level0RuleNr, 64), inputCombinationRuleCount, baseRuleCount, max2);

        final long level1RuleNr = RuleService.calcLevel1RuleNr(rule.ruleNr, baseRuleCount);
        System.out.printf("%s\n", toBinaryStr(level1RuleNr, 64));

        int pos = 0;
        for (int aMatrixStatePos = 0; aMatrixStatePos < rule.stateCount; aMatrixStatePos++) {
            for (int bMatrixStatePos = 0; bMatrixStatePos < rule.stateCount; bMatrixStatePos++) {
                for (int cMatrixStatePos = 0; cMatrixStatePos < rule.stateCount; cMatrixStatePos++) {
                    System.out.printf("%3d: ", pos);
                    System.out.printf("%1d ", aMatrixStatePos);
                    System.out.printf("%1d ", bMatrixStatePos);
                    System.out.printf("%1d ", cMatrixStatePos);

                    final int resultState = rule.ruleMatrix[aMatrixStatePos][bMatrixStatePos][cMatrixStatePos];

                    System.out.printf(" = %1d", resultState);
                    System.out.println();
                    pos++;
                }
            }
        }
    }
}