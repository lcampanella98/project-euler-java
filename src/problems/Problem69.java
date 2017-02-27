package problems;

import problems.tools.ESieve;

/**
 * Created by lorenzo on 2/12/2017.
 */
public class Problem69 extends Problem {

    int[] primes;

    @Override
    public String getSolution() {
/*
        int limit = 1000000;
        primes = ESieve.getPrimes((int)Math.sqrt(limit) + 10);
        int maxN = 0;
        double maxRatio = 0;
        for (int n = 1; n <= limit; n++) {
            double ratio =  n / phi(n);
            if (ratio > maxRatio) {
                maxN = n;
                maxRatio = ratio;
            }
        }
        return "Max N value is " + maxN + " with a ratio of " + maxRatio;
*/
        int limit = 1000000;
        phiSieve(limit);
        int maxN = 0;
        double maxRatio = 0, ratio;
        for (int i = 1; i < phis.length; i++) {
            ratio = i / phis[i].n;
            if (ratio > maxRatio) {
                maxN = i;
                maxRatio = ratio;
            }
        }
        return "Max n value was " + maxN + " with a ratio of " + maxRatio;
    }

    DoubleBool[] phis;

    void phiSieve(int limit) {
        DoubleBool[] phis = new DoubleBool[limit + 1];
        int i, j;
        double p;
        for (i = 0; i < phis.length; i++) {
            phis[i] = new DoubleBool(i, false);
        }
        phis[0].b = true;
        phis[1].b = true;
        int rtLim = (int) (Math.sqrt(phis.length) + 0.5);
        for (i = 2; i < rtLim; i++) {
            if (phis[i].b) continue;
            p = phis[i].n;
            for (j = i + i; j < phis.length; j += i) {
                phis[j].n *= 1.0 - 1.0 / p;
                phis[j].b = true;
            }
        }
        this.phis = phis;
    }

    double phi(int n) {
        int i = 0;
        int p;
        double phi = n;
        int rt = (int) (Math.sqrt(n));
        p = primes[i];
        while (p < rt) {
            if (n % p == 0) phi *= 1.0 - 1.0 / p;
            p = primes[++i];
        }
        return phi;
    }
}

class DoubleBool {
    double n;
    boolean b;
    public DoubleBool(double num, boolean bool) {n = num;b = bool;}
}
