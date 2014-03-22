/* Cat Taro has a string S. He wants to obtain the string "CAT" from the string S. In a single turn
he can choose any character and erase all occurrences of this character in S. He can do as many
turns as he wants (possibly zero).

You are given the String S. Return "Possible" (quotes for clarity) if it is possible to obtain the
string "CAT" and "Impossible" otherwise. */

public class TaroString {
    public static String getAnswer(String input) {
        String returnValue = "Impossible";
        int cIndex = -1;
        int aIndex = -1;
        int tIndex = -1;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch(c) {
            case 'C':
                if (cIndex != -1 || aIndex != -1 || tIndex != -1)
                    return returnValue;
                cIndex = i;
                break;
            case 'A':
                if (cIndex == -1 || aIndex != -1 || tIndex != -1)
                    return returnValue;
                aIndex = i;
                break;
            case 'T':
                if (cIndex == -1 || aIndex == -1 || tIndex != -1)
                    return returnValue;
                tIndex = i;
                break;
            }
        }
        returnValue = "Possible";
        return returnValue;
    }
}