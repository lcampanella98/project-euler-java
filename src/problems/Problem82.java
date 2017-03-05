package problems;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

class Problem82 extends Problem {

    private int[][] matrix;

    /**
     * We have an 80x80 matrix of positive integers.
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
                if (i == 0)
                    matrix = new int[parts.length][parts.length];
                for (j = 0; j < parts.length; j++)
                    matrix[i][j] = Integer.parseInt(parts[j]); // populate the matrix
                i++;
            }
        } catch (IOException e) {
            return "Error reading the file:\n" + e;
        }

        return getSmarterSolution();
    }

    /**
     * Works by finding the shorted path sums between
     *
     * @return The shortest 3-way path sum for the matrix
     */
    private String getEvenSmarterSolution() {
        int size = matrix.length;
        int[] sum = new int[size]; // stores the currently shortest path sums
        int i, j;
        for (i = 0; i < size; i++) sum[i] = matrix[i][0];

        for (j = 1; j < size; j++) {
            sum[0] += matrix[0][j];
            for (i = 1; i < size; i++) {
                sum[i] = Math.min(matrix[i][j] + sum[i], matrix[i][j] + sum[i - 1]);
            }

            for (i = size - 2; i >= 0; i--) {
                sum[i] = Math.min(sum[i], sum[i + 1] + matrix[i][j]);
            }
        }

        int minPath = sum[0];
        for (int pSum : sum) minPath = Math.min(minPath, pSum);
        return "The minimum 3-way path sum is " + minPath;
    }

    /**
     * Breaks the problem down by finding the minimum path sums for each pair of columns
     * and using that result for the next pair
     *
     * @return The answer
     */
    private String getSmarterSolution() {

        for (int col = 1; col < matrix.length; col++) minColumnPathSum(col);

        int minPathSum = minInCol(matrix.length - 1); // find the minimum path sum in the final column

        return "The minimum 3-way path sum is " + minPathSum;
    }

    /**
     * Find the minimum path sums going from column colIndex - 1
     * to column colIndex for each element in the column
     * (with the path moving only up, down, and right), and
     * overwrite the column in the matrix to those minimum path sums.
     *
     * @param colIndex The column for which we want to find
     *                 the minimum path sums
     */
    private void minColumnPathSum(int colIndex) {
        if (colIndex < 1 || colIndex >= matrix.length) return; // colIndex out of bounds

        int[] newMinCol = new int[matrix.length]; // create a temporary array for the new path sum column
        int row, row2, sum, accumulatedColSum;

        for (row = 0; row < matrix.length; row++) { // loop through each element in the column

            if (row > 0) // initialize the minimum path to the minimum path found directly "above"
                newMinCol[row] = newMinCol[row - 1] + matrix[row][colIndex];
            else
                newMinCol[row] = Integer.MAX_VALUE; // minimum path "above" is effectively infinity for first element

            accumulatedColSum = 0; // stores sum of elements in the column from index row to index row2
            for (row2 = row; row2 < matrix.length; row2++) { // check the path to the "left" and the paths "below"
                accumulatedColSum += matrix[row2][colIndex];

                // only continue if there is a possibility of getting a new minimum path sum
                if (accumulatedColSum < newMinCol[row]) {
                    sum = accumulatedColSum + matrix[row2][colIndex - 1]; // complete the path
                    newMinCol[row] = Math.min(newMinCol[row], sum); // take the minimum of previous and current sums
                } else break;
            }
        }
        for (row = 0; row < matrix.length; row++) // copy new minimum path sum column into the matrix
            matrix[row][colIndex] = newMinCol[row];
    }

    /**
     * Find the minimum element in column c of the matrix
     *
     * @param c The column in which to find the minimum element
     * @return The minimum element in column c
     */
    private int minInCol(int c) {
        int min = matrix[0][c];
        for (int r = 1; r < matrix.length; r++)
            min = Math.min(min, matrix[r][c]);
        return min;
    }

    /**
     * Brute force the problem by checking every path
     *
     * @return The answer
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

