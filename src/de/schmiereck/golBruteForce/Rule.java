package de.schmiereck.golBruteForce;

public class Rule {
    final int stateCount;
    final long ruleNr;
    final int cellCount;
    final int ruleMatrix[][][];
    final long level0RuleNr;
    final long level1RuleNr;

    public Rule(final int stateCount, final long ruleNr, final int cellCount, final long level0RuleNr, final long level1RuleNr) {
        this.stateCount = stateCount;
        this.ruleNr = ruleNr;
        this.cellCount = cellCount;
        this.level0RuleNr = level0RuleNr;
        this.level1RuleNr = level1RuleNr;
        this.ruleMatrix = new int[stateCount][stateCount][stateCount];
    }
}
