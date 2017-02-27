package problems;

import problems.tools.ESieve;
import problems.tools.Tools;

public class Problem70 extends Problem {
    @Override
    public String getSolution() {
        int limit = 10000000;
        int bestN = 0, bestPhi = 0;
        double minRatio = Double.MAX_VALUE, ratio;
        int[] primes = ESieve.getPrimes(2000, 5000);
        int pi, pj, n, phi;
        for (int i = 0, j; i < primes.length - 1; i++) {
            pi = primes[i];
            for (j = i + 1; j < primes.length; j++) {
                pj = primes[j];
                n = pi * pj;
                if (n >= limit) break;
                phi = (pi - 1) * (pj - 1);
                ratio = ((double)n) / phi;
                if (ratio < minRatio && Tools.isPerm(n, phi)) {
                    minRatio = ratio;
                    bestN = n;
                    bestPhi = phi;
                }
            }
        }
        return "Found n value of " + bestN + " with phi " + bestPhi + "\nWith ratio " + minRatio;
    }
}