class Solver {
    static int numberOfWays(int n) {
        return numberOfWays(n, 25);   
    }

    private static int numberOfWays(int n, int denom) {
        int nextDenom = 0;
        switch (denom) {
            case 25:
                nextDenom = 10;
                break;
            case 10:
                nextDenom = 5;
                break;
            case 5:
                nextDenom = 1;
                break;
            case 1:
                return 1;
        }
        int ways = 0;
        for (int i = 0; i * denom <= n; i++) {
            ways += numberOfWays(n - i * denom, nextDenom);
        }
        return ways;
    }
}

class CoinTest {
    public static void main(String[] args) {
        System.out.println(Solver.numberOfWays(10));
    }
}