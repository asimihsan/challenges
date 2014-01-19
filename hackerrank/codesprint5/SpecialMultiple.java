import java.util.BitSet;
import java.util.Scanner;

class SpecialMultiple {
    private static final int MAX_BITS = 32;

    public static long bitSetToLong(BitSet b) {
        long returnValue = 0;
        for (int i = b.nextSetBit(0); i >= 0; i = b.nextSetBit(i+1)) {
            long increment = 9;
            for (int j = i; j > 0; j--) {
                increment *= 10;
            } 
            returnValue += increment;
        }
        return returnValue;
    }

    public static long solve(int input) {
        BitSet b = new BitSet(MAX_BITS);
        b.set(0);
        long current;
        while (true) {
            current = bitSetToLong(b);
            if (current % input == 0)
                break;

            // Increment the BitSet
            int ncb = b.nextClearBit(0);
            b.set(ncb);
            b.clear(0, ncb);
        }
        return current;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.nextInt();
        while (scanner.hasNextInt()) {
            int input = scanner.nextInt();
            System.out.println(solve(input));
        }
    }
}