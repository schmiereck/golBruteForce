package de.schmiereck.golBruteForce;

public class RuleService {
    public static Rule createRule(final int stateCount, final long ruleNr, final int cellCount) {
        final long inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, inputCombinationRuleCount);
        final long level0RuleNr = RuleService.calcLevel0RuleNr(ruleNr, baseRuleCount);
        final long level1RuleNr = RuleService.calcLevel1RuleNr(ruleNr, baseRuleCount);

        final Rule rule = new Rule(stateCount, ruleNr, cellCount, level0RuleNr, level1RuleNr);

        for (int aMatrixStatePos = 0; aMatrixStatePos < stateCount; aMatrixStatePos++) {
            for (int bMatrixStatePos = 0; bMatrixStatePos < stateCount; bMatrixStatePos++) {
                for (int cMatrixStatePos = 0; cMatrixStatePos < stateCount; cMatrixStatePos++) {
                    final int bitPos =
                            (((aMatrixStatePos * stateCount * stateCount) +
                                    (bMatrixStatePos * stateCount)) +
                                    (cMatrixStatePos));
                    final int mask = 0b1;
                    //final int bit = (0b1 << bitPos);
                    final int bit = (mask << bitPos);

                    final long statePos = (level0RuleNr) % stateCount;
                    //final int bit = ((stateCount) << bitPos);
                    //final int resultState = ((Math.floorDiv((ruleNr + stateCount), stateCount) & bit) > 0) ? statePos : 0;
                    final long value1 = ((level0RuleNr / stateCount) & bit);
                    final long value = (level0RuleNr & bit);
                    final long state = (level1RuleNr & bit);
                    final int stateValue = (state != 0) ? 2 : 1;
                    final long resultState = (value != 0) ? stateValue : 0;

                    rule.ruleMatrix[aMatrixStatePos][bMatrixStatePos][cMatrixStatePos] = (int)resultState;
                }
            }
        }
        return rule;
    }

    public static Rule createRuleFor2States(final int stateCount, final int ruleNr, final int cellCount) {
        final long inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, inputCombinationRuleCount);
        final long level0RuleNr = RuleService.calcLevel0RuleNr(ruleNr, baseRuleCount);
        final long level1RuleNr = RuleService.calcLevel1RuleNr(ruleNr, baseRuleCount);

        final Rule rule = new Rule(stateCount, ruleNr, cellCount, level0RuleNr, level1RuleNr);

        for (int aMatrixStatePos = 0; aMatrixStatePos < stateCount; aMatrixStatePos++) {
            for (int bMatrixStatePos = 0; bMatrixStatePos < stateCount; bMatrixStatePos++) {
                for (int cMatrixStatePos = 0; cMatrixStatePos < stateCount; cMatrixStatePos++) {
                    final int bitPos =
                            (((aMatrixStatePos * stateCount * stateCount) +
                                    (bMatrixStatePos * stateCount)) +
                                    (cMatrixStatePos));

                    final int bit = (0b1 << bitPos);
                    final int resultState = ((level0RuleNr & bit) > 0) ? 1 : 0;

                    rule.ruleMatrix[aMatrixStatePos][bMatrixStatePos][cMatrixStatePos] = resultState;
                }
            }
        }
        return rule;
    }

    public static int calcInputCombinationBitCount(final int stateCount) {
        return (stateCount * stateCount * stateCount);
    }

    public static int calcInputCombinationRuleCount(final int stateCount) {
        return (((1 << (stateCount * stateCount)) << stateCount) << stateCount);
    }

    public static long calcInputCombinationRuleCount2(final int stateCount) {
        // stateCount: 2
        // 1 = 256 / (2 * 2 * 2 * 2 * 2 * 2 * 2 * 2)
        // 8 = ((3 * 2) + (2 * 1)
        // 8 = 6 + 2
        // 8 = 4 + 2 + 2
        // 8 = ((3 * 2) + (2 * 1) + (1 * 0)
        // 8 = 6 + 2 + 0
        // 8 = 2 * 2 * 2
        // 8 = (2 + 2) * 2
        // 8 = 4 + 3  + 1

        // 9 = 4 + 3 + 2
        // 9 = 6 + 3
        // 9 = (2 * 3) + (1 * 3)
        // 9 = 6 + 2 + 1
        // 9 = (2 * 3) + (1 * 2) + (1 * 1)

        // stateCount: 3
        // 1 = 32768 / 2 / 2 / 2 / 2 / 2 / 2 / 2 / 2 / 2 / 2 / 2 / 2 / 2 / 2 / 2
        // 1 = 32768 / (2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2)
        // 15 = 3 * 3 + 2 * 3
        // 15 = 5 * 3
        // 15 = ((2 * 3) + (3 * 3)) - 1
        // 15 = ((3 * 3) + (2 * 3))
        // 15 = 9 + 6
        // 15 = ((3 * 3) + (2 * 2) + (1 * 2))
        // 15 = 9 + 4 + 2

        // 15 = ((3 * 3) + (2 * 3))
        // 15 = 9 + 6
        // 15 = ((3 * 3) + (2 * 2) + (1 * 2))
        // 15 = 9 + 4 + 2
        // 15 = 8 + 4 + 2 + 1
        // 15 = 5 + 4 + 3 + 2 + 1
        // 15 = (3 + 2) * 3
        // 15 = 6 + 5 + 4

        // 16 = 9 + 6 + 1
        // 16 = (3 * 3) + (2 * 3) + 1
        final int bitCount = calcInputCombinationBitCount(stateCount);
       final long inputCombinationRuleCount = (1 << bitCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, bitCount);
        //final int shift = ((2 * stateCount) + (3 * stateCount));
        //final int shift = (((stateCount) * 3) + ((stateCount) * 2));
        return inputCombinationRuleCount;
    }

    public static int calcInputCombinationMax(final int stateCount) {
        return calcInputCombinationRuleCount(stateCount) - 1;
    }

    public static long calcStateRuleCount(int stateCount) {
        final long ms = stateCount - 1;
        final long fa = (ms * ms * ms);
        return (fa * fa * fa * fa);
    }

    public static long calcBaseRuleCount(int stateCount, long inputCombinationRuleCount) {
        final long stateRuleCount = calcStateRuleCount(stateCount);
        return ((inputCombinationRuleCount) * (stateRuleCount));
    }

    public static long calcBaseRuleMax(int stateCount, long inputCombinationRuleCount) {
        return calcBaseRuleCount(stateCount, inputCombinationRuleCount) - 1;
    }

    static long calcLevel0RuleNr(final long ruleNr, final long ruleCount) {
        //return ruleNr % (ruleCount + 1);
        return ruleNr % (ruleCount);
    }

    static long calcLevel1RuleNr(final long ruleNr, final long ruleCount) {
        return ruleNr / (ruleCount);
    }
}
