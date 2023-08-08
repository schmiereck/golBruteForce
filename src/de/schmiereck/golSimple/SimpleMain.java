package de.schmiereck.golSimple;


public class SimpleMain {
    private static final int stateCount = 2;
    public static void main(String[] args) {
        final SimpleRule[] ruleArr = new SimpleRule[256];
        initRules(ruleArr);
        //int ruleNr = 110; { //  http://atlas.wolfram.com/01/01/110/
        //int ruleNr = 66; { // 110 http://atlas.wolfram.com/01/01/66/
        for (int ruleNr = 0; ruleNr < 256; ruleNr++) {
            final SimpleRule recRule = ruleArr[ruleNr];
            final SimpleWorld recWorld = new SimpleWorld();
            recWorld.stateArr[recWorld.statePos] = 0b00010000;
            calcWorld(recWorld, recRule);
            printWorld(recWorld, recRule.ruleNr);
        }
    }

    private static void initRules(final SimpleRule[] ruleArr) {
        for (int ruleNr = 0; ruleNr < 256; ruleNr++) {
            final SimpleRule rule = createRuleFor2States(ruleNr);
            ruleArr[ruleNr] = rule;
        }
    }

    public static SimpleRule createRuleFor2States(final int ruleNr) {
        final SimpleRule rule = new SimpleRule();
        rule.ruleNr = ruleNr;

        for (int aMatrixStatePos = 0; aMatrixStatePos < stateCount; aMatrixStatePos++) {
            for (int bMatrixStatePos = 0; bMatrixStatePos < stateCount; bMatrixStatePos++) {
                for (int cMatrixStatePos = 0; cMatrixStatePos < stateCount; cMatrixStatePos++) {
                    final int bitPos = calcStatePos(aMatrixStatePos, bMatrixStatePos, cMatrixStatePos);

                    final int bit = (0b1 << bitPos);
                    final int resultState = ((ruleNr & bit) > 0) ? 1 : 0;

                    //rule.ruleMatrix[aMatrixStatePos][bMatrixStatePos][cMatrixStatePos] = resultState;
                    rule.ruleInToOutArr[bitPos] = resultState;
                }
            }
        }
        return rule;
    }

    private static int calcStatePos(final int aMatrixStatePos, final int bMatrixStatePos, final int cMatrixStatePos) {
        return ((aMatrixStatePos * stateCount * stateCount) +
                (bMatrixStatePos * stateCount)) +
                cMatrixStatePos;
    }

    private static void calcWorld(final SimpleWorld recWorld, final SimpleRule recRule) {
        for (int calcPos = 0; calcPos < 8; calcPos++) {
            final int inState = recWorld.stateArr[recWorld.statePos];
            int outState = 0;
            for (int bitPos = 0; bitPos < 8; bitPos++) {
                final int aState = calcState(inState, bitPos - 1);
                final int bState = calcState(inState, bitPos);
                final int cState = calcState(inState, bitPos + 1);
                //final int inValue = aState * 4 + bState * 2 + cState * 1;
                final int inValue = calcStatePos(aState, bState, cState);
                final int outValue = recRule.ruleInToOutArr[inValue];
                outState |= outValue << bitPos;
            }
            recWorld.statePos++;
            recWorld.stateArr[recWorld.statePos] = outState;
        }
    }

    private static int calcState(final int inState, final int bitPos) {
        final int bit = 1 << calcBitPos(bitPos);
        return (inState & bit) != 0 ? 1 : 0;
    }

    private static int calcBitPos(final int bitPos) {
        if (bitPos < 0) {
            return 8 + bitPos;
        } else {
            if (bitPos >= 8) {
                return bitPos % 8;
            } else {
                return bitPos;
            }
        }
    }

    private static void printWorld(final SimpleWorld recWorld, final int ruleNr) {
        System.out.printf("================ %3d\n", ruleNr);
        for (int pos = 0; pos < 8; pos++) {
            System.out.printf("%2d: ", pos);
            final int state = recWorld.stateArr[pos];
            for (int bitNr = 0; bitNr < 8; bitNr++) {
                final int bit = 1 << bitNr;
                final int value = (state & bit) != 0 ? 1 : 0;
                if (value == 1) {
                    System.out.print("1");
                } else {
                    System.out.print("-");
                }
            }
            System.out.printf(" %3d\n", state);
        }
    }
}
