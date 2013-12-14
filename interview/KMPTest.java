/**
 * Knuth-Morris-Pratt (KMP) string search, is O(m) to compute fail[] and
 * O(n) to search, so O(m + n) time and O(m) space.
 */
class KMP {
    private String pattern;
    private int[] fail;

    public KMP(String pattern) {
        this.pattern = pattern;
        int M = pattern.length();
        fail = new int[M];
        for (int i = 0, j = -1; i < M; i++) {
            if (i == 0)
                fail[i] = -1;
            else if (pattern.charAt(i) != pattern.charAt(j))
                fail[i] = j;
            else
                fail[i] = fail[j];
            while (j >= 0 && pattern.charAt(i) != pattern.charAt(j))
                j = fail[j];
            j++;
        }
    }

    public int search(String text) {
        int M = pattern.length();
        int N = text.length();
        int i, j;
        for (i = 0, j = 0; i < N && j < M; i++) {
            while (j >= 0 && text.charAt(i) != pattern.charAt(j))
                j = fail[j];
            j++;
        }
        if (j == M) return i - M;
        return -1;
    }
}

class KMPTest {
    public static void main(String[] args) {
        KMP kmp = new KMP("abaab");
        System.out.println(kmp.search("aaaaaabbbaabbaaabababbabaabbbba"));
    }
}