package problems;

public abstract class Problem implements Runnable {

    /**
     * @return The solution to the problem as a String
     */
    public abstract String getSolution();

    /**
     * Run and output the solution and output the execution time to the console
     */
    public void run() {
        long start = System.nanoTime();
        String sol = getSolution();
        long end = System.nanoTime();
        System.out.println(sol);
        System.out.println("-------------------------------\nCode executed in "
                + (end - start) / 1000000.0 + " ms");
    }
}
