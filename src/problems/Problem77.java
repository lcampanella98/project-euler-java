package problems;

import problems.tools.ESieve;

/**
 * Created by lorenzo on 2/22/2017.
 */
public class Problem77 extends Problem {

    @Override
    public String getSolution() {
        int lim = 100;
        int targetWays = 5000;
        int result = 0;
        primes = ESieve.getPrimes(lim);
        ways = new int[lim + 1];
        ways[0] = 1;


        for (int i = 0, v; i < primes.length; i++) {
            for (v = primes[i]; v <= lim; v++) {
                ways(v, i);
            }
        }
        for (int v = 0; v < ways.length; v++) {
            if (ways[v] > targetWays) {
                result = v;
                break;
            }
        }
        return "First num to be written as sum of primes in over " + targetWays + " different ways is " + result;
    }

    private void ways(int v, int i) {
        ways[v] += ways[v - primes[i]];
    }

    private int maxN;
    private int[] primes;
    private int[] ways;
}
