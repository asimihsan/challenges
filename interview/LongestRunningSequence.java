import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

class LongestRunningSequence {
    static void updateRuns(Map<Character, Integer> runs, Character character, int length) {
        if (!(runs.containsKey(character)))
            runs.put(character, 0);
        runs.put(character, runs.get(character) + length);
    }

    static void updatePrefixesOrSuffixes(Map<Character, Integer> map, Character character,
                                         int length) {
        if (!(map.containsKey(character)))
            map.put(character, 0);
        map.put(character, Math.max(length, map.get(character)));
    }

    static void longestRunningSequence(String[] array) {
        Map<Character, Integer> prefixes = new HashMap<Character, Integer>();
        Map<Character, Integer> suffixes = new HashMap<Character, Integer>();
        Map<Character, Integer> runs = new HashMap<Character, Integer>();

        for(String string : array) {
            int length = string.length();
            if (length == 1) {
                updateRuns(runs, string.charAt(0), length);
                continue;
            }
            char prefix = string.charAt(0);
            int prefixLength = 1;
            boolean inPrefix = true;
            for(int i = 1; i < length && inPrefix; i++) {
                if (string.charAt(i) == prefix)
                    prefixLength++;
            }

            char suffix = string.charAt(length - 1);
            int suffixLength = 1;
            boolean inSuffix = true;
            for (int i = length - 2; i >= 0 && inSuffix; i--) {
                if (string.charAt(i) == suffix)
                    suffixLength++;
            }

            if (prefixLength == length && suffixLength == length) {
                // this is a run!
                assert(prefix == suffix);
                updateRuns(runs, prefix, length);
            } else {
                updatePrefixesOrSuffixes(prefixes, prefix, prefixLength);
                updatePrefixesOrSuffixes(suffixes, suffix, suffixLength);
            }
        }

        Set<Character> allCharacters = new HashSet<Character>();
        allCharacters.addAll(prefixes.keySet());
        allCharacters.addAll(suffixes.keySet());
        allCharacters.addAll(runs.keySet());

        int max = Integer.MIN_VALUE;
        Character result = null;
        for (Character c : allCharacters) {
            int charLength = (prefixes.containsKey(c) ? prefixes.get(c) : 0) +
                             (suffixes.containsKey(c) ? suffixes.get(c) : 0) +
                             (runs.containsKey(c) ? runs.get(c) : 0);
            if (charLength > max) {
                max = charLength;
                result = c;
            }
        }
        assert(result != null);
        System.out.println(String.format("%s,%s", result, max));
    }

    public static void main(String[] args) {
        String[] array = {
            //"ab",
            //"ba",
            //"aac",
            "aa",
            "dd",
        };
        longestRunningSequence(array);
    }
}