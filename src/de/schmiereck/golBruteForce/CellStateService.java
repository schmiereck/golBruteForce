package de.schmiereck.golBruteForce;

public class CellStateService {
    public static int calcInitCellState(final int initPos, final int initCellPos) {
        return ((initPos & (0b1 << initCellPos)) > 0) ? 1 : 0;
    }
}
