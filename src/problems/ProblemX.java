package problems;

import java.util.Stack;

/**
 * Created by lorenzo on 3/1/2017.
 */
public class ProblemX extends Problem {

    @Override
    public String getSolution() {

        return null;
    }

    public boolean representsPreOrderTraversal(int[] a) {
        Stack<Integer> s = new Stack<>();

        int root = Integer.MIN_VALUE;

        for (int i = 0; i < a.length; i++) {
            if (a[i] < root) return false;
            while (!s.empty() && s.peek() < a[i]) {
                root = s.pop();
            }
            s.push(a[i]);
        }
        return true;
    }

}
