/**
 * q5.1: parity checking
 */

class ParityTest {
    static int[] parity_lookup = new int[65536];
    static {
        for (int i = 0; i < 65536; i++)
            parity_lookup[i] = naive(i);
    }

    public static int naive(long input) {
        int parity;
        for (parity = 0; input != 0; input >>= 1)
            if ((input & 1) == 1) parity ^= 1;
        return parity;
    }

    public static int parity1(long input) {
        final int mask = 0xFFFF;
        int parity;
        for (parity = 0; input != 0; input >>= 16) {
            parity ^= parity_lookup[(int)(input & mask)];
        }
        return parity;
    }

    public static void main(String[] args) {
        System.out.println(naive(0b00000001));
        System.out.println(naive(0b00000011));
        System.out.println(naive(0b00000111));

        System.out.println(parity1(0b00001111));
        System.out.println(parity1(0b00011111));

    }    
}