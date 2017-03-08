package problems;


import problems.tools.ESieve;

import java.util.HashSet;
import java.util.Set;

public class Problem87 extends Problem {
    @Override
    public String getSolution() {
        int limit = 50000000;
        int[] primes = ESieve.getPrimes((int) Math.sqrt(limit - Math.pow(2, 3) - Math.pow(2, 4)));
        int[][] primePows = new int[3][primes.length];
        int i, j;
        for (j = 0; j < primes.length; j++) {
            primePows[0][j] = primes[j] * primes[j];
        }
        for (i = 1; i < primePows.length; i++) {
            for (j = 0; j < primes.length; j++)
                primePows[i][j] = primePows[i - 1][j] * primes[j];
        }

        int n0, n1, n2, c = 0;
        Set<Integer> nums = new HashSet<>();
        for (int i0 = 0; i0 < primes.length; i0++) {
            n0 = primePows[0][i0];
            for (int i1 = 0; i1 < primes.length; i1++) {
                n1 = n0 + primePows[1][i1];
                if (n1 + 16 > limit) break;
                for (int i2 = 0; i2 < primes.length; i2++) {
                    n2 = n1 + primePows[2][i2];
                    if (n2 > limit) break;
                    nums.add(n2);
                }
            }
        }
        return nums.size() + " numbers below " + limit + " can be expressed as such";
    }
}
