/**
 * http://community.topcoder.com/stat?c=problem_statement&pm=12643&rd=15696
 * http://apps.topcoder.com/wiki/display/tc/SRM+584
 */

import java.util.Set;
import java.util.HashSet;

public class TopFox {
    public int possibleHandles(String familyName, String givenName) {
        Set<String> handles = new HashSet<String>();
        for (int i = 0, n = familyName.length(); i < n; i++) {
            for (int j = 0, m = givenName.length(); j < m; j++) {
                String handle = familyName.substring(0, i+1) +
                                givenName.substring(0, j+1);
                handles.add(handle);
            }
        }
        return handles.size();
    }
}

class TopCoderTopFox {
    public static void main(String[] args) {
        TopFox topFox = new TopFox();
        System.out.println(topFox.possibleHandles(
            "abb",
            "bbc"
        ));
    }
}