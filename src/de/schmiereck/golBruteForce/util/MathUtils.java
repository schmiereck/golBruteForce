package de.schmiereck.golBruteForce.util;

import de.schmiereck.golBruteForce.RuleService;

public abstract class MathUtils {

    public static long invertRuleNr(final long ruleNr, final int stateCount) {
        final long inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleMax = RuleService.calcBaseRuleMax(stateCount, inputCombinationRuleCount);
        //final long inputCombinationMax = RuleService.calcInputCombinationMax(stateCount);
        final long invertedRuleNr = (~ruleNr) & baseRuleMax;
        return invertedRuleNr;
    }

    public static long mirrorRuleNr(final long ruleNr, final int stateCount) {
        final long inputCombinationBitCount = RuleService.calcInputCombinationBitCount(stateCount);
        long mirroredRuleNr = 0;
        int bit = 1;
        for (int pos = 0; pos < inputCombinationBitCount; pos++) {
            final int resultBit = (ruleNr & bit) != 0 ? 1 : 0;
            mirroredRuleNr |= resultBit << ((inputCombinationBitCount - 1) - pos);
            bit = bit << 1;
        }
        return mirroredRuleNr;
    }

    public static long previousRuleNr(final long ruleNr, final int stateCount) {
        return 0L;
    }

    /**
     * @return State corresponds to a previous rule with fewer states?
     */
    public static boolean checkPreviousRule(final long ruleNr, final int stateCount) {
        /*
        final boolean ret;

        final int previousStateCount = stateCount - 1;
        final long inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(previousStateCount);
        final long baseRuleMax = RuleService.calcBaseRuleMax(previousStateCount, inputCombinationRuleCount);
        //final long inputCombinationMax = RuleService.calcInputCombinationMax(stateCount);

        if (ruleNr < baseRuleMax) {
            ret = true;
        } else {
            ret = false;
        }

        return ret;
        */
        return false;
    }
}
