import java.util.Collection;
import java.util.HashSet;

class SubstringPalindromes {
    public static Collection<String> palindromes(String input) {
        HashSet<String> returnValue = new HashSet<String>();
        String inputReversed = new StringBuilder(input).reverse().toString();
        int maxIndex = input.length() - 1;
        for (int substringSize = 0; substringSize <= maxIndex; substringSize++) {
            for (int i = 0; i <= maxIndex - substringSize; i++) {
                String substring = input.substring(i,
                                                   i + substringSize + 1);
                String substringReversed = inputReversed.substring(maxIndex - i - substringSize,
                                                                   maxIndex - i + 1);
                if (substring.equals(substringReversed))
                    returnValue.add(substring);
            }
        }
        return returnValue;
    }

    public static void main(String[] args) {
        System.out.println(palindromes("abba")); // [a, b, bb, abba]
        System.out.println(palindromes("abccbd")); // [d, b, c, bccb, a, cc]
        System.out.println(palindromes("aaabbb")); // [a, b, aa, bb, aaa, bbb]
        System.out.println(palindromes("abcdefgh")); // [a, b, c, d, e, f, g, h]
        System.out.println(palindromes("")); // []
        System.out.println(palindromes("a")); // [a]
    }
}