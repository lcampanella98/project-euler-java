package problems;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lorenzo on 2/15/2017.
 */
public class Problem74 extends Problem {

    int[] facts;

    @Override
    public String getSolution() {
        facts = new int[10];
        facts[0] = 1;
        for (int i = 1; i < facts.length; i++) facts[i] = facts[i - 1] * i;
        int limit = 1000000;
        int numChains = 0;
        Set<Integer> chain;
        int chainCt;
        for (int i = 1; i < limit; i++) {
            chainCt = 0;
            int last = i;
            chain = new HashSet<>();
            while (chainCt <= 60) {
                chain.add(last);
                chainCt++;
                last = factDigitSum(last);
                if (chain.contains(last)) break;
            }
            if (chainCt == 60) numChains++;
        }
        return "The number of chains with exactly 60 links is " + numChains;
    }

    public int factDigitSum(int n) {
        int sum = 0;
        while (n > 0) {
            sum += facts[n % 10];
            n /= 10;
        }
        return sum;
    }

}
