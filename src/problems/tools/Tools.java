package problems.tools;

/**
 * Created by lorenzo on 1/26/2017.
 */
public class Tools {
    public static boolean isPrime(int n) {
        if (n == 2 || n == 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        int rt = (int) Math.sqrt(n);
        if (rt * rt != n) rt++;
        int t = 6;
        while (t - 1 <= rt) {
            if (n % (t - 1) == 0) return false;
            if (t + 1 > rt) return true;
            if (n % (t + 1) == 0) return false;
            t += 6;
        }
        return true;
    }

    public static boolean isPerm(int a, int b) {
        int[] dCounts = new int[10];
        while (a > 0) {
            dCounts[a % 10]++;
            a /= 10;
        }
        while (b > 0) {
            dCounts[b % 10]--;
            b /= 10;
        }
        for (int i = 0; i < dCounts.length; i++) if (dCounts[i] != 0) return false;
        return true;
    }
}
