class CodingForInterviewsReverseWords {
    public static void swap(byte[] input, int i, int j) {
        byte temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }

    public static int findNextWordBoundary(byte[] input, int i) {
        while (i < input.length && input[i] != ' ')
            i++;
        return (i - 1);
    }

    public static void reverseWord(byte[] input, int i, int j) {
        while (i < j)
            swap(input, i++, j--);
    }

    public static void reverseString(byte[] input) {
        for (int i = 0, j = input.length - 1; i < j; i++, j--)
            swap(input, i, j);
        int i = 0, j;
        while (true) {
            j = findNextWordBoundary(input, i);
            reverseWord(input, i, j);
            if (j == input.length - 1)
                break;
            i = j + 2;
        }
    }

    public static void reverseWords(byte[] input) {
        reverseString(input);
    }

    public static void main(String[] args) {
        byte[] input = "a big fluffy hippo".getBytes();
        reverseWords(input);
        System.out.println(new String(input));
    }
}