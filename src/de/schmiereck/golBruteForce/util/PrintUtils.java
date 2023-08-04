package de.schmiereck.golBruteForce.util;

public abstract class PrintUtils {

    public static String toBinaryStr(final long x, final int len) {
        final StringBuilder result = new StringBuilder();

        for (int i = len - 1; i >= 0 ; i--) {
            final long mask = 1L << i;
            result.append((x & mask) != 0L ? 1 : 0);
        }
        return result.toString();
    }
}
