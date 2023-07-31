package de.schmiereck.golBruteForce;

public class World {
    public final Map mapArr[];
    public int actMapdPos = 0;
    public final int size;
    public final int historyCount;
    public WorldService.Stat complexityStat = new WorldService.Stat();;
    public WorldService.Stat energyStat = new WorldService.Stat();;
    public WorldService.Stat mass0PointPosStat = new WorldService.Stat();;
    public WorldService.Stat statMiddleAsymmetryAsymmetryStat = new WorldService.Stat();;
    public WorldService.Stat statMiddleAsymmetryWeightedAsymmetryStat = new WorldService.Stat();;
    public WorldService.Stat statMassPointAsymmetryAsymmetryStat = new WorldService.Stat();;
    public WorldService.Stat statMassPointAsymmetryWeightedAsymmetryStat = new WorldService.Stat();;

    public World(final int size, final int historyCount) {
        this.size = size;
        this.historyCount = historyCount;
        if (historyCount < 2) {
            throw new RuntimeException("historyCount must be equal or greater than 2.");
        }
        this.mapArr = new Map[historyCount];
        for (int historyPos = 0; historyPos < historyCount; historyPos++) {
            this.mapArr[historyPos] = new Map(size);
        }
    }
}
