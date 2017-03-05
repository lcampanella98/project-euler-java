package problems;

import java.io.*;
import java.util.*;

public class Problem83 extends Problem{

    private class Node implements Comparable<Node> {
        List<Node> adj;
        int val;
        int pathSum;
        boolean visited;
        Node bestPathNode;

        Node(int val) {
            this(val, null);
        }

        Node(int val, List<Node> adj) {
            this.val = val;
            this.adj = adj;
            visited = false;
            pathSum = Integer.MAX_VALUE;
        }

        @Override
        public int compareTo(Node o) {
            if (pathSum > o.pathSum) return 1;
            if (pathSum < o.pathSum) return -1;
            return 0;
        }
    }


    public String getSolution() {
        String fName = "res/p083_matrix.txt";
        int matrixLen = 80;
        int i, j;
        Node[][] matrix = new Node[matrixLen][matrixLen];
        try (BufferedReader r = new BufferedReader(new FileReader(fName))) {
            String[] line;
            i = 0;
            while (r.ready()) {
                line = r.readLine().split(",");
                for (j = 0; j < line.length; j++) {
                    matrix[i][j] = new Node(Integer.parseInt(line[j]));
                }
                ++i;
            }
        } catch (IOException e) {
            return "Error reading from file:\n" + e;
        }
        // generate graph

        List<Node> adjTemp;

        for (i = 0; i < matrix.length; i++) {
            for (j = 0; j < matrix.length; j++) {
                adjTemp = new ArrayList<>();
                if (i > 0) adjTemp.add(matrix[i-1][j]);
                if (j > 0) adjTemp.add(matrix[i][j-1]);
                if (i < matrixLen - 1) adjTemp.add(matrix[i+1][j]);
                if (j < matrixLen - 1) adjTemp.add(matrix[i][j+1]);
                matrix[i][j].adj = adjTemp;
            }
        }

        // dijkstra
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Node start = matrix[0][0], end = matrix[matrixLen - 1][matrixLen - 1];
        start.pathSum = start.val;
        queue.add(start);
        Node cur;
        while (true) {
            if ((cur = queue.poll()) == null) break;
            for (Node n : cur.adj) {
                if (n.visited) continue;
                if (cur.pathSum + n.val < n.pathSum) {
                    n.pathSum = cur.pathSum + n.val;
                    n.bestPathNode = cur;
                    queue.add(n);
                }
            }

            cur.visited = true;
        }

        return "The best 4-way path sum was " + end.pathSum;
    }


}

