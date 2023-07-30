package de.schmiereck.golBruteForce;

import static de.schmiereck.golBruteForce.util.PrintUtils.toBinary;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World of Life!");

        calcSimple();
        //calcComplex();
    }

    private static void calcSimple() {
        final int size = 32 + 1;
        final int middleSizePos = size / 2;

        final int historyCount = 12;

        final int initSize = 1;//7;//5;//3;
        final int initCnt = 1 << initSize;
        final int startSizePos = middleSizePos + (initSize / 2);

        final int cellCount = 3;
        final int stateCount = 2;//3;
        calc(size, historyCount, initSize, initCnt, startSizePos, cellCount, stateCount);
    }

    private static void calcComplex() {
        final int size = 32 + 1;
        final int middleSizePos = size / 2;

        final int historyCount = 12;

        final int initSize = 7;//5;//3;
        final int initCnt = 1 << initSize;
        final int startSizePos = middleSizePos + (initSize / 2);

        final int cellCount = 3;
        final int stateCount = 3;
        calc(size, historyCount, initSize, initCnt, startSizePos, cellCount, stateCount);
    }

    private static void calc(final int size, final int historyCount, final int initSize, final int initCnt,
                             final int startSizePos, final int cellCount, final int stateCount) {
        final int inputCombinationRuleCount = RuleService.calcInputCombinationRuleCount(stateCount);
        final long baseRuleCount = RuleService.calcBaseRuleCount(stateCount, inputCombinationRuleCount);
        for (int ruleNr = 0; ruleNr < baseRuleCount; ruleNr++) {
            final Rule rule = RuleService.createRule(stateCount, ruleNr, cellCount);
            showRule(rule);

            for (int initPos = 1; initPos < initCnt; initPos++) {
                System.out.printf("initPos: %2d\n", initPos);

                final World world = new World(size, historyCount);

                for (int initCellPos = 0; initCellPos < initSize; initCellPos++) {
                    WorldService.submitCellState(world, startSizePos - initCellPos,
                            CellStateService.calcInitCellState(initPos, initCellPos));
                }
                WorldService.calcStat(world, world.mapArr[world.actMapdPos], size);

                for (int runPos = 0; runPos < historyCount; runPos++) {
                    showWorld(world, runPos);
                    WorldService.calc(world, rule);
                }
            }
        }
    }

    private static void showRule(final Rule rule) {
        System.out.println("=================================================================================");
        System.out.printf("Rule-Nr: %,d [%,d + %,d]\n", rule.ruleNr, rule.level0RuleNr, rule.level1RuleNr);
        System.out.printf("         %s\n", toBinary(rule.level0RuleNr, 32));
        System.out.printf("         %s\n", toBinary(rule.level1RuleNr, 32));

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

    private static void showWorld(final World world, final int runPos) {
        final Map map = world.mapArr[world.actMapdPos];
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
}