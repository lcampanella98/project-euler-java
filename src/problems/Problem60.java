package problems;

import problems.tools.ESieve;
import problems.tools.Tools;

import java.util.*;

/**
 * Created by lorenzo on 1/26/2017.
 */
public class Problem60 extends Problem {

    int pLim = 30000;
    int[] primes;
    String[] primesStr;
    Set<Pair> goodPairs;
    Set<Pair> badPairs;
    int numPrimes;
    List<String> winningSet;

    @Override
    public String getSolution() {
        primes = ESieve.getPrimes(pLim);
        goodPairs = new HashSet<>();
        badPairs = new HashSet<>();
        numPrimes = 5;
        primesStr = new String[primes.length];
        for (int i = 0; i < primes.length; i++) primesStr[i] = Integer.toString(primes[i]);
        nextTrial(new ArrayList<>(), numPrimes, 0);
        int sum = sumStringIntList(winningSet);
        return "The lowest sum is " + sum + " with the winning set " + Arrays.toString(winningSet.toArray());
    }

    public int sumStringIntList(List<String> l) {
        int s = 0;
        for (String x : l) s += Integer.parseInt(x);
        return s;
    }

    public void nextTrial(List<String> currSet, int numLeftToChoose, int i) {
        if (numLeftToChoose == 0) {
            if (currSet.size() == numPrimes) {
                if (winningSet == null) winningSet = currSet;
                else if (sumStringIntList(currSet) < sumStringIntList(winningSet)) winningSet = currSet;
            }
            return;
        }

        numLeftToChoose--;
        int lim = primesStr.length - numLeftToChoose;
        String pi;
        while (i < lim) {
            pi = primesStr[i];
            if (numLeftToChoose == numPrimes - 1) System.out.println(pi);
            boolean areAllPairs = true;
            for (String pj : currSet) {
                if (!isPairPrime(pi, pj)) {
                    areAllPairs = false;
                    break;
                }
            }
            if (areAllPairs) {
                currSet.add(pi);
                nextTrial(currSet, numLeftToChoose, i + 1);
                currSet.remove(pi);
            }
            i++;
        }
    }

    public boolean isPairPrime(String i, String j) {
        return Tools.isPrime(Integer.parseUnsignedInt(i.concat(j)))
                && Tools.isPrime(Integer.parseUnsignedInt(j.concat(i)));
    }


}

class Pair {
    Integer i, j;

    public Pair(int a, int b) {
        i = a;
        j = b;
    }

    @Override
    public int hashCode() {
        if (i > j) return 31 * i + j;
        else return 31 * j + i;
    }
}