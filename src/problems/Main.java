package problems;


public class Main {

    public static void main(String[] args) {
        Problem p = new Problem82();

        if (args.length > 0) {
            try {
                p = (Problem) Main.class.getClassLoader().loadClass("problems.Problem" + args[0]).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                System.out.println("Invalid problem");
            }
        }

        p.run();
    }
}