class CodingForInterviewsBitManipulation {
    public static int setBit(int number, int index, boolean set) {
        if (set)
            return number | (1 << index);
        else
            return number & (~(1 << index));
    }

    public static boolean getBit(int number, int index) {
        int result = (number & (1 << index)) >> index;
        return (result == 1);
    }

    public static void main(String[] args) {
        int input = 0b11100110;

        //        = 0b11101110
        System.out.println(Integer.toString(setBit(input, 3, true), 2));

        //        = 0b11100100
        System.out.println(Integer.toString(setBit(input, 1, false), 2));

        // false
        System.out.println(getBit(input, 0));

        // true
        System.out.println(getBit(input, 7));
    }
}