package problems;

public class Problem31 extends Problem {

    @Override
    public String getSolution() {
        return smart();
    }

    int[] ways;

    public String smart() {
        int targetChange = 200;
        ways = new int[targetChange + 1];
        ways[0] = 1;
        for (int i = 0; i < c.length; i++) {
            for (int v = c[i]; v < ways.length; v++)
                ways(v, i);
        }
        return "Made " + targetChange + " in " + ways[targetChange] + " ways.";
    }

    private void ways(int v, int i) {
        ways[v] += ways[v - c[i]];
    }


    public String bruteForce() {
        nextCoin(0, 0);
        return "Made 200p in " + wayCount + " wayCount";
    }

    int[] c = {1,2,5,10,20,50,100,200};
    int[] memo;
    int wayCount = 0;

    private void nextCoin(int i, int sum) {
        if (i == c.length - 1 || sum == 200) {
            wayCount++;
        }
        else if (i < c.length) {
            for (int val = 0; sum + val <= 200; val += c[i]) {
                nextCoin(i + 1, sum + val);
            }
        }
    }


}
