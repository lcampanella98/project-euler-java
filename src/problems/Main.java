package problems;


public class Main {

    public static void main(String[] args) {
        Problem p = new Problem82();

        if (args.length > 0) {
            try {
                p = (Problem) Main.class.getClassLoader().loadClass("Problem"+args[0]).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }

        p.run();
    }
}