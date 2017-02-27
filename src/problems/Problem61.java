package problems;

import java.util.*;

/**
 * Created by lorenzo on 2/9/2017.
 */
public class Problem61 extends Problem {

    HashMap<PolygonalNumber, List<Set<CyclicNum>>> startsWith, endsWith;
    List<CyclicNum> nums;

    @Override
    public String getSolution() {
        startsWith = new HashMap<>(PolygonalNumber.values().length);
        endsWith = new HashMap<>(PolygonalNumber.values().length);
        for (PolygonalNumber pn : PolygonalNumber.values()) {
            List<Set<CyclicNum>> starts = new ArrayList<>(100), ends = new ArrayList<>(100);
            for (int i = 0; i < 100; i++) {
                starts.add(new HashSet<>());
                ends.add(new HashSet<>());
            }
            startsWith.put(pn, starts);
            endsWith.put(pn, ends);
        }

        boolean allDone = false;
        for (int n = 1; !allDone; n++) {
            int[] nums = {
                    getTriangular(n),
                    getSquare(n),
                    getPentagonal(n),
                    getHexagonal(n),
                    getHeptagonal(n),
                    getOctagonal(n)
            };
            for (int i = 0; i < nums.length; i++) {
                int num = nums[i];
                if (num > 999 && num < 10000) addNum(num, PolygonalNumber.values()[i]);
                else if (num > 9999 && i == 0) allDone = true;
            }
        }
        Set<PolygonalNumber> pn = new HashSet<>();
        Collections.addAll(pn, PolygonalNumber.values());
        findNextNum(new Stack<>(), pn, 6);
        if (nums != null) {
            int sum = 0;
            for (CyclicNum cn : nums) sum += cn.val;
            return "The numbers are " + nums.toString() + " with sum " + sum;

        } else return "not found";
    }

    private boolean findNextNum(Stack<CyclicNum> curNums, Set<PolygonalNumber> typesLeft, int left) {
        Set<PolygonalNumber> typesLeftCopy = new HashSet<>(typesLeft);
        if (left == 6) {
            for (PolygonalNumber t : typesLeft) {
                for (Set<CyclicNum> s : startsWith.get(t)) {
                    for (CyclicNum cn : s) {
                        curNums.push(cn);
                        typesLeftCopy.remove(t);
                        if (findNextNum(curNums, typesLeftCopy, left - 1)) {
                            nums = curNums;
                            return true;
                        }
                        typesLeftCopy.add(t);
                        curNums.pop();
                    }
                }
            }
        }
        CyclicNum top = curNums.peek();
        if (left == 0) {
            return top.matchesBefore(curNums.firstElement());
        }

        for (PolygonalNumber t : typesLeft) {
            for (CyclicNum cn : startsWith.get(t).get(top.end)) {
                curNums.push(cn);
                typesLeftCopy.remove(t);
                if (findNextNum(curNums, typesLeftCopy, left - 1)) {return true;}
                typesLeftCopy.add(t);
                curNums.pop();
            }
        }
        return false;
    }

    private void addNum(int num, PolygonalNumber type) {
        CyclicNum cn = new CyclicNum(num, type);
        startsWith.get(type).get(cn.start).add(cn);
        endsWith.get(type).get(cn.end).add(cn);
    }

    public static int getTriangular(int n) {
        return (n * (n + 1)) / 2;
    }

    public static int getSquare(int n) {
        return n * n;
    }

    public static int getPentagonal(int n) {
        return (n * (3 * n - 1)) / 2;
    }

    public static int getHexagonal(int n) {
        return n * (2 * n - 1);
    }

    public static int getHeptagonal(int n) {
        return (n * (5 * n - 3)) / 2;
    }

    public static int getOctagonal(int n) {
        return n * (3 * n - 2);
    }
}

enum PolygonalNumber {
    TRIANGLE, SQUARE, PENTAGONAL, HEXAGONAL, HEPTAGONAL, OCTAGONAL
}

class CyclicNum {
    PolygonalNumber type;
    int val, start, end;

    public CyclicNum(int v, PolygonalNumber t) {
        val = v;
        start = v / 100;
        end = v % 100;
        type = t;
    }

    public boolean matchesBefore(CyclicNum o) {
        return end == o.start;
    }

    public boolean matchesAfter(CyclicNum o) {
        return start == o.end;
    }

    public String toString() {return Integer.toString(val);}
}
