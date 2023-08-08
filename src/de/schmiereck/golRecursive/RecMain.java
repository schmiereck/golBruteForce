package de.schmiereck.golRecursive;

public class RecMain {
    private static final int stateCount = 2;
    public static void main(String[] args) {
        final RecRule[] ruleArr = new RecRule[256];
        initRules(ruleArr);
        //int ruleNr = 110; { //  http://atlas.wolfram.com/01/01/110/
        //int ruleNr = 66; { // 110 http://atlas.wolfram.com/01/01/66/
        for (int stateNr = 0; stateNr < 256; stateNr++) {
            final RecWorld recWorld = new RecWorld();
            recWorld.stateArr[recWorld.statePos] = stateNr;
            calcWorld(recWorld, ruleArr);
            printWorld(recWorld, stateNr);
        }
    }

    private static void initRules(final RecRule[] ruleArr) {
        for (int ruleNr = 0; ruleNr < 256; ruleNr++) {
            final RecRule rule = createRuleFor2States(ruleNr);
            ruleArr[ruleNr] = rule;
        }
    }

    public static RecRule createRuleFor2States(final int ruleNr) {
        final RecRule rule = new RecRule();
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

    private static void calcWorld(final RecWorld recWorld, final RecRule[] ruleArr) {
        for (int calcPos = 0; calcPos < 256; calcPos++) {
            final int inState = recWorld.stateArr[recWorld.statePos];
            //final RecRule recRule = ruleArr[255 - inState];
            final RecRule recRule = ruleArr[inState];
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

    private static void printWorld(final RecWorld recWorld, final int stateNr) {
        System.out.printf("================ %3d\n", stateNr);
        int lastState = -1;
        for (int pos = 0; pos < 256; pos++) {
            final int state = recWorld.stateArr[pos];
            if (lastState == state) {
                break;
            } else {
                lastState = state;
            }
            System.out.printf("%3d: ", pos);
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
