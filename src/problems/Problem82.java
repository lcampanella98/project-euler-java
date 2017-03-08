package problems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Problem82 extends Problem {

    private int[][] matrix;
    private int size;

    /**
     * We have an square matrix of positive integers.
     * Finds the path with the minimum sum that travels
     * from the leftmost column of the matrix to the rightmost
     * column, where the path can move up, down, and right.
     *
     * @return The sum of the minimum path for the matrix
     */
    public String getSolution() {
        String fName = "res/p082_matrix.txt";

        try (BufferedReader r = new BufferedReader(new FileReader(fName))) {
            String line;
            String[] parts;
            int i = 0, j;
            while (r.ready()) {
                line = r.readLine();
                parts = line.split(",");
                if (i == 0) {
                    size = parts.length;
                    matrix = new int[size][size];
                }
                for (j = 0; j < size; j++)
                    matrix[i][j] = Integer.parseInt(parts[j]);
                i++;
            }
        } catch (IOException e) {
            return "Error reading the file:\n" + e;
        }

        return getSmarterSolution();
    }

    /**
     * Breaks the problem down by finding the minimum path sums for each successive column
     * and using that result for the next column
     *
     * @return The minimum 3-way path sum
     */
    private String getSmarterSolution() {

        // the minimum path sums to get to the 0th column are already "solved"
        for (int col = 1; col < size; col++)
            minColumnPathSum(col);

        int finalColumn = size - 1;

        int min = matrix[0][finalColumn];
        for (int row = 1; row < size; row++)
            min = Math.min(min, matrix[row][finalColumn]);

        return "The minimum 3-way path sum is " + min;
    }

    /**
     * Finds the minimum path sums to get to each element in (col)
     * with the path moving only up, down, and right.
     *
     * Assumes that the previous column (col-1) contains
     * the minimum path sums to get to its respective elements.
     *
     * Overwrites the column in the matrix to the newly found minimum path sums.
     *
     * @param col The column for which we want to find
     *            the minimum path sums
     */
    private void minColumnPathSum(int col) {

        int[] newCol = new int[size]; // temp array for the path sums to each respective element in the column

        newCol[0] =
                matrix[0][col] // first element in current column
                + matrix[0][col - 1]; // path sum coming from the left

        int row;
        for (row = 1; row < matrix.length; row++) { // traverse down, while looking up and left
            newCol[row] = Math.min(
                    newCol[row - 1], // path from above
                    matrix[row][col - 1]) // path from the left
                    + matrix[row][col];
        }
        for (row = matrix.length - 2; row > -1; row--) { // traverse up, while looking down
            newCol[row] = Math.min(
                    newCol[row], // smallest path looking up and left
                    newCol[row + 1] + matrix[row][col]); // path from below
        }

        for (row = 0; row < matrix.length; row++) // copy new path sum column into matrix
            matrix[row][col] = newCol[row];
    }

    /**
     * Brute force the problem by checking every path
     *
     * @return The minimum 3-way path sum
     */
    private String bruteForce() {
        minSum = Integer.MAX_VALUE;
        nextStepInPath(0, 0, 0);
        return "The minimum 3-way path sum is " + minSum;
    }

    private int minSum;

    private void nextStepInPath(int col, int lastRow, int pathSum) {
        if (col >= matrix.length) {
            minSum = Math.min(minSum, pathSum);
            return;
        }
        int row, row2, colSum;
        if (col == 0) {
            for (row = 0; row < matrix.length; row++) {
                nextStepInPath(col + 1, row, matrix[row][col]);
            }
            return;
        }
        for (row = 0; row < matrix.length; row++) {
            colSum = 0;

            if (row < lastRow) {
                for (row2 = row; row2 <= lastRow; row2++)
                    colSum += matrix[row2][col];
            } else if (row > lastRow) {
                for (row2 = row; row2 >= lastRow; row2--)
                    colSum += matrix[row2][col];
            } else
                colSum = matrix[row][col];

            if (pathSum + colSum >= minSum) break;
            nextStepInPath(col + 1, row, pathSum + colSum);
        }
    }
}

