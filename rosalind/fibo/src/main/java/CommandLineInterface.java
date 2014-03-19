public class CommandLineInterface {
    public static void main(String[] args) {
        int n = new Integer(args[0]);
        System.out.println(Fibonacci.calculate(n));
    }
}
