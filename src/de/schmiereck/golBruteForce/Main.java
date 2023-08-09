package de.schmiereck.golBruteForce;

import static de.schmiereck.golBruteForce.CalcService.calcInitRunFilter;
import static de.schmiereck.golBruteForce.util.MathUtils.checkPreviousRule;
import static de.schmiereck.golBruteForce.util.MathUtils.invertRuleNr;
import static de.schmiereck.golBruteForce.util.MathUtils.mirrorRuleNr;
import static de.schmiereck.golBruteForce.util.MathUtils.previousRuleNr;
import static de.schmiereck.golBruteForce.util.PrintUtils.toBinaryStr;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World of Life!");

        //calcSimpleSimpleInit(true);
        //calcSimpleForInit(false, 1, 1, false);
        //calcSimpleForInit(false, 2, 3, false);
        calcSimpleForInit(false, 3, 5, false);
        //calcSimpleComplexInit(false);
        //calcComplexSimpleInit(true);
        //calcComplex(true);
    }

    private static void calcSimpleSimpleInit(final boolean showMinimal) {
        final int size = 32 + 1;

        final int historyCount = 16 + 1;

        final int initSize = 1;//7;//5;//3;
        final int initCnt = 1 << initSize;

        final int cellCount = 3;
        final int stateCount = 2;//3;
        calc(size, historyCount, initSize, initCnt, cellCount, stateCount, showMinimal);
    }

    private static void calcSimpleForInit(final boolean showMinimal, final int initSize, final int initPos, final boolean useFilter) {
        final int size = 32 + 1;

        final int historyCount = 16 + 1;

        final int cellCount = 3;
        final int stateCount = 2;//3;
        calcForInitPos(size, historyCount, cellCount, stateCount, showMinimal, initSize, initPos, useFilter);
    }

    private static void calcSimpleComplexInit(final boolean showMinimal) {
        final int size = 32 + 1;

        final int historyCount = 16 + 1;

        final int initSize = 7;//5;//3;
        final int initCnt = 1 << initSize;

        final int cellCount = 3;
        final int stateCount = 2;//3;
        calc(size, historyCount, initSize, initCnt, cellCount, stateCount, showMinimal);
    }

    private static void calcComplexSimpleInit(final boolean showMinimal) {
        final int size = 32 + 1;

        final int historyCount = 16 + 1;

        final int initSize = 1;
        final int initCnt = 1 << initSize;

        final int cellCount = 3;
        final int stateCount = 3;
        calc(size, historyCount, initSize, initCnt, cellCount, stateCount, showMinimal);
    }

    private static void calcComplex(final boolean showMinimal) {
        final int size = 32 + 1;

        final int historyCount = 16 + 1;

        final int initSize = 7;//5;//3;
        final int initCnt = 1 << initSize;

        final int cellCount = 3;
        final int stateCount = 3;
        calc(size, historyCount, initSize, initCnt, cellCount, stateCount, showMinimal);
    }

    private static void calc(final int size, final int historyCount, final int initSize, final int initCnt,
                             final int cellCount, final int stateCount, final boolean showMinimal) {
        for (int initPos = 1; initPos < initCnt; initPos++) {
            calcForInitPos(size, historyCount, cellCount, stateCount, showMinimal, initSize, initPos, true);
        }
    }

    private static void calcForInitPos(int size, int historyCount, int cellCount, int stateCount, boolean showMinimal,
                                       int initSize, int initPos, final boolean useFilter) {
        final int inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, inputCombinationRuleCount);
        for (long ruleNr = 0; ruleNr < baseRuleCount; ruleNr++) {
        //int ruleNr = 90; {
            if (showMinimal == false) {
                System.out.println("=================================================================================");
            }
            final CalcService.RuleCheck ruleCheck = CalcService.checkRule(ruleNr, stateCount);

            if (ruleCheck == CalcService.RuleCheck.Run) {
                final Rule rule = RuleService.createRule(stateCount, ruleNr, cellCount);
                if (showMinimal == false) {
                    showRule(rule, false);
                }

                final World world = new World(size, historyCount);

                final FilterResult filterResult = calcInitRunFilter(rule, world, initSize, initPos);
                final boolean notFiltered;
                if (useFilter) {
                    notFiltered = CalcService.checkFilterResult(filterResult);
                } else {
                    notFiltered = true;
                }

                if (notFiltered) {
                    if (showMinimal == false) {
                        System.out.printf("initPos: %2d\n", initPos);
                        showWorld(world);
                    } else {
                        showRuleMinimal(rule);
                    }
                }
            } else {
                if (showMinimal == false) {
                    showRuleCheck(ruleNr, stateCount, ruleCheck);
                }
            }
        }
    }

    private static void showRuleCheck(final long ruleNr, final int stateCount, final CalcService.RuleCheck ruleCheck) {
        final long checkedRuleNr =
        switch (ruleCheck) {
            case Inverted -> invertRuleNr(ruleNr, stateCount);
            case Mirror -> mirrorRuleNr(ruleNr, stateCount);
            case InvertedMirror -> invertRuleNr(mirrorRuleNr(ruleNr, stateCount), stateCount);
            case PreviousRule -> previousRuleNr(ruleNr, stateCount);
            case Run -> ruleNr;
        };
        System.out.printf("Rule-Nr: %,d ignored - Rule is %s to Rule %,d\n", ruleNr, ruleCheck.toString(), checkedRuleNr);
    }

    private static void showRuleMinimal(final Rule rule) {
        System.out.printf("Rule-Nr: %,4d [%,4d + %,4d]\n", rule.ruleNr, rule.level0RuleNr, rule.level1RuleNr);
    }

    private static void showRule(final Rule rule, final boolean showDetailedRule) {
        System.out.printf("Rule-Nr: %,d [%,d + %,d]\n", rule.ruleNr, rule.level0RuleNr, rule.level1RuleNr);
        final int inputCombinationBitCount = RuleService.calcInputCombinationBitCount(rule.stateCount);
        System.out.printf("         %s\n", toBinaryStr(rule.level0RuleNr, inputCombinationBitCount));
        System.out.printf("         %s\n", toBinaryStr(rule.level1RuleNr, inputCombinationBitCount));

        if (showDetailedRule) {
            for (int aMatrixStatePos = 0; aMatrixStatePos < rule.stateCount; aMatrixStatePos++) {
                for (int bMatrixStatePos = 0; bMatrixStatePos < rule.stateCount; bMatrixStatePos++) {
                    for (int cMatrixStatePos = 0; cMatrixStatePos < rule.stateCount; cMatrixStatePos++) {
                        System.out.printf("%1d ", aMatrixStatePos);
                        System.out.printf("%1d ", bMatrixStatePos);
                        System.out.printf("%1d ", cMatrixStatePos);

                        final int resultState = rule.ruleMatrix[aMatrixStatePos][bMatrixStatePos][cMatrixStatePos];

                        System.out.printf(" = %1d", resultState);
                        System.out.println();
                    }
                }
            }
        }
    }

    private static void showWorld(final World world) {
        for (int runPos = 0; runPos < world.historyCount; runPos++) {
            final Map map = world.mapArr[runPos];
            final int size = map.cellArr.length;
            System.out.printf("%2d: ", runPos);

            for (int cellPos = 0; cellPos < size; cellPos++) {
                final Cell cell = map.cellArr[cellPos];

                if (cell.state == 0) {
                    System.out.print("-");
                } else {
                    System.out.printf("%1d", cell.state);
                }
            }
            System.out.printf(" | %3d | %3d | %3d | %3d | %3d | %3d | %3d", map.statComplexity, map.statEnergy, map.mass0PointPos,
                    map.statMiddleAsymmetry.asymmetry, map.statMiddleAsymmetry.weightedAsymmetry,
                    map.statMassPointAsymmetry.asymmetry, map.statMassPointAsymmetry.weightedAsymmetry);
            System.out.println();
        }
        System.out.print(" ".repeat(4 + world.size));
        System.out.printf(" | %3d | %3d | %3d | %3d | %3d | %3d | %3d \n",
                world.complexityStat.absAverage, world.energyStat.absAverage, world.mass0PointPosStat.absAverage,
                world.statMiddleAsymmetryAsymmetryStat.absAverage, world.statMiddleAsymmetryWeightedAsymmetryStat.absAverage,
                world.statMassPointAsymmetryAsymmetryStat.absAverage, world.statMassPointAsymmetryWeightedAsymmetryStat.absAverage);
        System.out.print(" ".repeat(4 + world.size));
        System.out.printf(" | %3d | %3d | %3d | %3d | %3d | %3d | %3d \n",
                world.complexityStat.diffAverage, world.energyStat.diffAverage, world.mass0PointPosStat.diffAverage,
                world.statMiddleAsymmetryAsymmetryStat.diffAverage, world.statMiddleAsymmetryWeightedAsymmetryStat.diffAverage,
                world.statMassPointAsymmetryAsymmetryStat.diffAverage, world.statMassPointAsymmetryWeightedAsymmetryStat.diffAverage);
    }
}