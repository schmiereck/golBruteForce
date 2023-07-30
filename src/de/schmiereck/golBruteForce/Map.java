package de.schmiereck.golBruteForce;

public class Map {
    public class Asymmetry {
        public int asymmetry;
        public int weightedAsymmetry;
    }
    public final Cell cellArr[];
    public int statComplexity = 0;
    public int statEnergy = 0;
    public int mass0PointPos = -1;
    public Asymmetry statMiddleAsymmetry = new Asymmetry();
    public Asymmetry statMassPointAsymmetry = new Asymmetry();

    public Map(final int size) {
        this.cellArr = new Cell[size];
        for (int cellPos = 0; cellPos < size; cellPos++) {
            this.cellArr[cellPos] = new Cell();
        }
    }
}
