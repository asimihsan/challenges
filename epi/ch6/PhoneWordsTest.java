/**
 * Given a cell phone keypad, specified by mapping M that maps individual digits
 * and returns the corresponding set of characters) and a number sequence, return
 * all possible character sequences (not just legal words) that correspond to the
 * number sequence.
 * 
 * 2 -> ABC
 * 3 -> DEF
 * 4 -> GHI
 * 5 -> JKL
 * 6 -> MNO
 * 7 -> PQRS
 * 8 -> TUV
 * 9 -> WXYZ
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PhoneWords {
    private static final Map<Character, Collection<Character>> lookup = new HashMap<>();
    static {
        lookup.put('2', Arrays.asList('A', 'B', 'C'));
        lookup.put('3', Arrays.asList('D', 'E', 'F'));
        lookup.put('4', Arrays.asList('G', 'H', 'I'));
        lookup.put('5', Arrays.asList('J', 'K', 'L'));
        lookup.put('6', Arrays.asList('M', 'N', 'O'));
        lookup.put('7', Arrays.asList('P', 'Q', 'R', 'S'));
        lookup.put('8', Arrays.asList('T', 'U', 'V'));
        lookup.put('9', Arrays.asList('W', 'X', 'Y', 'Z'));
    }

    public Collection<String> getWords(String number) {
        return getWords(number, 0, "");
    }

    private Collection<String> getWords(String number, int index, String path) {
        if (index == number.length())
            return Arrays.asList(path);
        List<String> words = new ArrayList<>();
        for (Character c : lookup.get(number.charAt(index)))
            words.addAll(getWords(number, index + 1, path + c));
        return words;
    }
}

class PhoneWordsTest {
    public static void main(String[] args) {
        PhoneWords p = new PhoneWords();
        System.out.println(p.getWords("234"));
    }
}
