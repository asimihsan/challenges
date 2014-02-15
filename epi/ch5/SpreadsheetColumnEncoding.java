class SpreadsheetColumnEncoding {
    public static int ssDecodeColID(String input) {
        if (input.length() == 0)
            throw new IllegalArgumentException("input cannot be zero-length");
        int value = 0;
        for (int i = input.length() - 1, power = 0; i >= 0; i--, power++) {
            char c = input.charAt(i);
            int multiple = (int)Math.pow(26, power);
            value += multiple * (c - 'A' + 1);
        }
        return value;
    }

    public static void main(String[] args) {
        System.out.println(ssDecodeColID("A"));  // 1
        System.out.println(ssDecodeColID("B"));  // 2
        System.out.println(ssDecodeColID("Z"));  // 26
        System.out.println(ssDecodeColID("AA"));  // 27
        System.out.println(ssDecodeColID("AB"));  // 28
    }
}