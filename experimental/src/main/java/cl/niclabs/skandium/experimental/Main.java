package cl.niclabs.skandium.experimental;

public class Main {
    public static void main(String[] args) {
        System.out.println("Number of Threads: " + Runtime.getRuntime().availableProcessors());
        if (args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                System.out.println("arg[" + i + "] : " + args[i]);
            }
        } else {
            System.out.println("no arguments given");
        }
    }
}
