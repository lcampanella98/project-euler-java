package problems;

/**
 * Created by lorenzo on 1/26/2017.
 */
public class Problem1 extends Problem{

    @Override
    public String getSolution() {
        int lim = 1000;
        int sum = sumSeq(3, 3, lim) + sumSeq(5, 5, lim) - sumSeq(15, 15, lim);
        return "The sum is " + sum;
    }

    private int sumSeq(int a0, int d, int lim) {
        int n = (lim - a0) / d + 1;
        if ((n - 1) * d == lim - a0) n--;
        return a0 * n + (d * n * (n - 1)) / 2;
    }
}
