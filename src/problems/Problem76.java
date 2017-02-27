package problems;

/**
 * Created by lorenzo on 2/22/2017.
 */
public class Problem76 extends Problem {
    @Override
    public String getSolution() {
        int target = 100;
        ways = new int[target + 1];
        ways[0] = 1;
        for (int i = 1, v; i < target; i++) {
            for (v = i; v <= target; v++) {
                ways(v, i);
            }
        }
        return "Made " + target + " in " + ways[target] + " ways.";
    }

    private int[] ways;

    private void ways(int v, int i) {
        ways[v] += ways[v - i];
    }
}
