package problems.tools;


public class ESieve {

    public static int[] getPrimes(int upper) {
        int len = upper + 1;
        boolean[] flags = new boolean[len];
        int listLen = 0;
        for (int i = 2; i < len; i++) {
            if (flags[i]) continue;
            listLen++;
            for (int j = i + i; j < len; j += i) flags[j] = true;
        }
        int[] primes = new int[listLen];
        for (int i = 2, j = 0; i < len; i++) if (!flags[i]) primes[j++] = i;
        return primes;
    }

    public static int[] getPrimes(int lower, int upper) {
        int len = upper + 1;
        boolean[] flags = new boolean[len];
        int listLen = 0, startIndex = 2;
        boolean shouldCount = false;
        for (int i = 2; i < len; i++) {
            if (flags[i]) continue;
            if (!shouldCount) {
                shouldCount = i >= lower;
                if (shouldCount) {
                    listLen++;
                    startIndex = i;
                }
            } else listLen++;

            for (int j = i + i; j < len; j += i) flags[j] = true;
        }
        int[] primes = new int[listLen];
        for (int i = startIndex, j = 0; i < len; i++) if (!flags[i]) primes[j++] = i;
        return primes;
    }
}
