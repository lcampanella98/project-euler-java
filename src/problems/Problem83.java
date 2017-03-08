package problems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Problem83 extends Problem {

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

    private Node[][] matrix;
    private int size;

    public String getSolution() {
        String fName = "res/p083_matrix.txt";
        int i, j;

        try (BufferedReader r = new BufferedReader(new FileReader(fName))) {
            String[] line;
            i = 0;
            while (r.ready()) {
                line = r.readLine().split(",");
                if (i == 0) {
                    size = line.length;
                    matrix = new Node[size][size];
                }
                for (j = 0; j < size; j++) {
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
                if (i > 0) adjTemp.add(matrix[i - 1][j]); // add node above
                if (i < size - 1) adjTemp.add(matrix[i + 1][j]); // add node below
                if (j > 0) adjTemp.add(matrix[i][j - 1]); // add node left
                if (j < size - 1) adjTemp.add(matrix[i][j + 1]); // add node right
                matrix[i][j].adj = adjTemp;
            }
        }

        // dijkstra's algorithm
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Node start = matrix[0][0], end = matrix[size - 1][size - 1];
        start.pathSum = start.val;
        queue.add(start);
        Node cur;
        while ((cur = queue.poll()) != null) { // loop while the queue is not empty
            for (Node n : cur.adj) { // go through unvisited adjacent nodes
                if (n.visited) continue;
                if (cur.pathSum + n.val < n.pathSum) { // check for a smaller path sum
                    n.pathSum = cur.pathSum + n.val;
                    n.bestPathNode = cur; // at the end if we want, we can recreate the path
                    queue.add(n); // insert the node into the queue
                }
            }
            cur.visited = true;
        }

        return "The best 4-way path sum was " + end.pathSum;
    }
}