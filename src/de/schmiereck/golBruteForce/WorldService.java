package de.schmiereck.golBruteForce;

public class WorldService {
    public static void calc(final World world, final Rule rule) {
        final int nextMapdPos = (world.actMapdPos + 1) % world.historyCount;
        final Map actMap = world.mapArr[world.actMapdPos];
        final Map nextMap = world.mapArr[nextMapdPos];
        final int size = world.size;

        for (int cellPos = 0; cellPos < size; cellPos++) {
            final Cell cell = nextMap.cellArr[cellPos];
            cell.state = calcNexCellState(rule,
                    actMap.cellArr[calcSavePos(world, cellPos - 1)],
                    actMap.cellArr[cellPos],
                    actMap.cellArr[calcSavePos(world, cellPos + 1)]);
        }

        calcStat(world, nextMap, size);
        world.actMapdPos = nextMapdPos;
    }

    static void calcStat(final World world, final Map nextMap, final int size) {
        final int middlePos = size / 2;
        int mass0PointPos = -1;
        int mass0PointCnt = 0;
        for (int cellPos = 0; cellPos < size; cellPos++) {
            final Cell cell = nextMap.cellArr[cellPos];
            final int haveState = (cell.state != 0 ? 1 : 0);
            nextMap.statComplexity += haveState;
            nextMap.statEnergy += cell.state;
            if (haveState != 0) {
                if (mass0PointPos == -1) {
                    mass0PointPos = cellPos;
                } else {
                    mass0PointPos += cellPos;
                }
                mass0PointCnt++;
            }
            if (cellPos < middlePos) {
                calcAsymmetry(world, nextMap, cellPos, cell, middlePos, nextMap.statMiddleAsymmetry);
            }
        }
        if (mass0PointCnt > 0) {
            nextMap.mass0PointPos = Math.floorDiv(mass0PointPos, mass0PointCnt);

            for (int cellPos = 0; cellPos < middlePos; cellPos++) {
                final Cell cell = nextMap.cellArr[cellPos];

                calcAsymmetry(world, nextMap, cellPos, cell, nextMap.mass0PointPos, nextMap.statMassPointAsymmetry);
            }
        }
    }

    private static void calcAsymmetry(World world, Map nextMap, int cellPos, Cell cell, final int pos, final Map.Asymmetry asymmetry) {
        final int diffToPos = pos - cellPos;
        final Cell otherCell = nextMap.cellArr[calcSavePos(world, pos + diffToPos)];
        asymmetry.asymmetry += Math.abs(cell.state - otherCell.state);
        asymmetry.weightedAsymmetry += Math.abs(Math.abs(cell.state - otherCell.state) * diffToPos);
    }

    private static int calcNexCellState(final Rule rule, final Cell lCell, final Cell aCell, final Cell rCell) {
        final int ret;
        /*
        final int sum = lCell.state + aCell.state + rCell.state;

        if (sum == 3) {
            ret = 0;
        } else {
            if ((aCell.state == 0) && (sum == 2)) {
                ret = 1;
            } else {
                ret = aCell.state;
            }
        }
        */
        ret = rule.ruleMatrix[lCell.state][aCell.state][rCell.state];
        return ret;
    }

    private static int calcSavePos(final World world, int cellPos) {
        if (cellPos < 0) {
            return world.size + cellPos;
        } else {
            if (cellPos >= world.size) {
                return cellPos % world.size;
            } else {
                return cellPos;
            }
        }
    }

    public static void submitCellState(final World world, final int cellPos, final int state) {
        final Map map = world.mapArr[world.actMapdPos];
        map.cellArr[cellPos].state = state;
    }

    public static class Stat {
        public int count = 0;
        public int lastAbs = -1;
        public int absSum = 0;
        public int absAverage = 0;
        public int diffSum = 0;
        public int diffAverage = 0;
    }

    public static void calcWorldStat(final World world, final Rule rule) {
        for (int runPos = 0; runPos < world.historyCount; runPos++) {
            final Map map = world.mapArr[runPos];

            calcRunStat(world.complexityStat, map.statComplexity);
            calcRunStat(world.energyStat, map.statEnergy);
            calcRunStat(world.mass0PointPosStat, map.mass0PointPos);
            calcRunStat(world.statMiddleAsymmetryAsymmetryStat, map.statMiddleAsymmetry.asymmetry);
            calcRunStat(world.statMiddleAsymmetryWeightedAsymmetryStat, map.statMiddleAsymmetry.weightedAsymmetry);
            calcRunStat(world.statMassPointAsymmetryAsymmetryStat, map.statMassPointAsymmetry.asymmetry);
            calcRunStat(world.statMassPointAsymmetryWeightedAsymmetryStat, map.statMassPointAsymmetry.weightedAsymmetry);
        }
        calcStatAverage(world.complexityStat);
        calcStatAverage(world.energyStat);
        calcStatAverage(world.mass0PointPosStat);
        calcStatAverage(world.statMiddleAsymmetryAsymmetryStat);
        calcStatAverage(world.statMiddleAsymmetryWeightedAsymmetryStat);
        calcStatAverage(world.statMassPointAsymmetryAsymmetryStat);
        calcStatAverage(world.statMassPointAsymmetryWeightedAsymmetryStat);
    }

    private static void calcRunStat(final Stat stat, final int abs) {
        stat.absSum += abs;
        if (stat.lastAbs != -1) {
            stat.diffSum += Math.abs(stat.lastAbs - abs);
        }
        stat.count++;
        stat.lastAbs = abs;
    }
    private static void calcStatAverage(final Stat stat) {
        stat.absAverage = stat.absSum / stat.count;
        stat.diffAverage = stat.diffSum / stat.count;
    }
}
