import java.util.Arrays;
import java.util.Comparator;


class StringComparators {
    public static class StringLengthAscendingComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            if (s1.length() < s2.length()) return -1;
            else if (s1.length() == s2.length()) return 0;
            else return 1;
        }

    }

    public static class StringLengthDescendingComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            if (s1.length() < s2.length()) return 1;
            else if (s1.length() == s2.length()) return 0;
            else return -1;
        }
    }

    public static String[] sortLengthAscending(String[] input) {
        String[] result = Arrays.copyOf(input, input.length);
        Arrays.sort(result, new StringLengthAscendingComparator());
        return result;
    }

    public static String[] sortLengthDescending(String[] input) {
        String[] result = Arrays.copyOf(input, input.length);
        Arrays.sort(result, new StringLengthDescendingComparator());
        return result;
    }
}

class Comparators {
    public static void main(String[] args) {
        String[] foo = {"12345", "1234567", "1", "12"};
        System.out.println(Arrays.deepToString(StringComparators.sortLengthAscending(foo)));
        System.out.println(Arrays.deepToString(StringComparators.sortLengthDescending(foo)));
    }
}