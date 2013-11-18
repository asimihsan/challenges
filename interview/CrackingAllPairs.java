import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class CrackingAllPairs {
    static List<String> allPairs(int n) {
        if (n == 1) return new ArrayList<String>(Arrays.asList(new String[] {"()"}));
        Set<String> returnValue = new HashSet<String>();
        for (String combination : allPairs(n - 1)) {
            for (int i = 0; i < combination.length(); i++) {
                for (int j = i; j < combination.length(); j++) {
                    StringBuilder result = new StringBuilder();
                    for (int k = 0; k < combination.length(); k++) {
                        if (k == i)
                            result.append("(");
                        if (k == j)
                            result.append(")");
                        result.append(combination.charAt(k));
                    }
                    returnValue.add(result.toString());
                }
            }
        }
        return new ArrayList<String>(returnValue);
    }

    public static void main(String[] args) {
        System.out.println(allPairs(2));
        System.out.println(allPairs(3));
    }
}