package de.schmiereck.golBruteForce.util;

public abstract class PrintUtils {

    public static String toBinaryStr(long x, int len)
    {
        StringBuilder result = new StringBuilder();

        for (int i = len - 1; i >= 0 ; i--)
        {
            int mask = 1 << i;
            result.append((x & mask) != 0 ? 1 : 0);
        }

        return result.toString();
    }
}
