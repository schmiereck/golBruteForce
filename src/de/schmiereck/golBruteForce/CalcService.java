package de.schmiereck.golBruteForce;

import static de.schmiereck.golBruteForce.util.MathUtils.checkPreviousRule;
import static de.schmiereck.golBruteForce.util.MathUtils.invertRuleNr;
import static de.schmiereck.golBruteForce.util.MathUtils.mirrorRuleNr;

public class CalcService {

    public static FilterResult calcInitRunFilter(Rule rule, World world, int initSize, int initPos) {
        final int middleSizePos = world.size / 2;
        final int startSizePos = middleSizePos + (initSize / 2);

        calcInit(world, initSize, initPos, startSizePos);
        WorldService.calcStat(world, world.mapArr[world.actMapdPos], world.size);

        for (int runPos = 1; runPos < world.historyCount; runPos++) {
            WorldService.calc(world, rule);
        }
        WorldService.calcWorldStat(world, rule);

        final FilterResult filterResult = filterWorldOfInteresst(world);
        return filterResult;
    }

    public static void calcInit(World world, int initSize, int initPos, int startSizePos) {
        for (int initCellPos = 0; initCellPos < initSize; initCellPos++) {
            WorldService.submitCellState(world, startSizePos - initCellPos,
                    CellStateService.calcInitCellState(initPos, initCellPos));
        }
    }

    private static FilterResult filterWorldOfInteresst(final World world) {
        final FilterResult retFilterResult = new FilterResult();

        for (int runPos = 1; runPos < world.historyCount; runPos++) {
            final int lastRunPos = runPos - 1;
            final Map lastMap = world.mapArr[lastRunPos];
            final Map map = world.mapArr[runPos];
            if (retFilterResult.staticAfter == -1) {
                if (map.statComplexity == lastMap.statComplexity) {
                    retFilterResult.staticAfter = lastRunPos;
                }
            }
            if (retFilterResult.staticDeathAfter == -1) {
                if ((map.statComplexity == 0) && (map.statComplexity == lastMap.statComplexity)) {
                    retFilterResult.staticDeathAfter = lastRunPos;
                }
            }
        }

        if ((world.complexityStat.absAverage == 1) && (world.energyStat.absAverage == 1) &&
                (world.complexityStat.diffAverage == 0) && (world.energyStat.diffAverage == 0)) {
            retFilterResult.particle = true;
        }
        return retFilterResult;
    }

    enum RuleCheck {
        Run,
        PreviousRule,
        Inverted,
        Mirror,
        InvertedMirror
    }
    public static RuleCheck checkRule(final long ruleNr, final int stateCount) {
        final RuleCheck retRuleCheck;

        if (checkPreviousRule(ruleNr, stateCount)) {
            retRuleCheck = RuleCheck.PreviousRule;
        } else {
            final long invertedRuleNr = invertRuleNr(ruleNr, stateCount);
            if (invertedRuleNr < ruleNr) {
                retRuleCheck = RuleCheck.Inverted;
            } else {
                final long mirroredRuleNr = mirrorRuleNr(ruleNr, stateCount);
                if (mirroredRuleNr < ruleNr) {
                    retRuleCheck = RuleCheck.Mirror;
                } else {
                    final long invertedMirroredRuleNr = invertRuleNr(mirroredRuleNr, stateCount);
                    if (invertedMirroredRuleNr < ruleNr) {
                        retRuleCheck = RuleCheck.InvertedMirror;
                    } else {
                        retRuleCheck = RuleCheck.Run;
                    }
                }
            }
        }

        return retRuleCheck;
    }

    public static boolean checkFilterResult(final FilterResult filterResult) {
        //if (((filterResult.staticAfter == -1) || (filterResult.staticAfter > 3))) {
        return ((filterResult.staticDeathAfter == -1) || (filterResult.staticDeathAfter > 4)) &&
                (filterResult.particle == true);
    }
}
