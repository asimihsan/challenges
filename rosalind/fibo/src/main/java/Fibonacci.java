public class Fibonacci {
    public static int calculate(int n) {
        if (n == 0 || n == 1)
            return n;
        return calculate(n - 1) + calculate(n - 2);
    }
}