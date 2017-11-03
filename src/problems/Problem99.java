package problems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Problem99 extends Problem {

    @Override
    public String getSolution() {
        try(BufferedReader br = new BufferedReader(new FileReader("res/p099_base_exp.txt"))) {
            String line;
            String[] spl;
            double v, maxVal = 0;
            int maxLn = -1, ln = 1;
            while (br.ready()) {
                line = br.readLine();
                spl = line.split(",");
                int b = Integer.parseInt(spl[0]);
                int e = Integer.parseInt(spl[1]);
                v = e * Math.log10(b);
                if (v > maxVal) {
                    maxVal = v;
                    maxLn = ln;
                }
                ln++;
            }
            return "line " + maxLn;
        }catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
