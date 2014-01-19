import java.math.BigInteger;
import java.util.Scanner;

class IsFibo {
    private static final BigInteger FOUR = new BigInteger("4");
    private static final BigInteger FIVE = new BigInteger("5");

    public static BigInteger bigIntSqRootFloor(BigInteger x) {
        // square roots of 0 and 1 are trivial and
        // y == 0 will cause a divide-by-zero exception
        if (x == BigInteger.ZERO || x == BigInteger.ONE) {
            return x;
        } // end if
        BigInteger two = BigInteger.valueOf(2L);
        BigInteger y;
        // starting with y = x / 2 avoids magnitude issues with x squared
        for (y = x.divide(two);
                y.compareTo(x.divide(y)) > 0;
                y = ((x.divide(y)).add(y)).divide(two));
        return y;
    } // end bigIntSqRootFloor

    public static boolean isPerfectSquare(BigInteger input) {
        BigInteger sqrt = bigIntSqRootFloor(input);
        return sqrt.multiply(sqrt).equals(input);
    }

    public static boolean isFibonacciNumber(BigInteger input) {
        BigInteger portion = input.pow(2).multiply(FIVE);
        return (isPerfectSquare(portion.add(FOUR)) ||
                isPerfectSquare(portion.subtract(FOUR)));
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        input.nextInt();
        while (input.hasNextBigInteger()) {
            BigInteger candidate = input.nextBigInteger();
            if (isFibonacciNumber(candidate))
                System.out.println("IsFibo");
            else
                System.out.println("IsNotFibo");
        }
    }
}