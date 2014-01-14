import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class SplitLines {
    /**
     * Wrap one big string to a boundary, e.g. 80 characters for a
     * terminal. Returns a list of strings that a client will
     * probably join with a line feed character.
     *
     * e.g. "i am a foobar fabulous yes", 6 becomes
     * ["i am a", "foobar", "fabul-", "ous y-", "es"]
     * 
     * @param  input Input string to split. Assume no line breaks
     *               present.
     * @return       List of strings, one per line.
     */
    public static List<String> splitLines(String input, int length) {
        ArrayList<String> returnValue = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (builder.length() == length) {
                returnValue.add(builder.toString());
                builder = new StringBuilder();
            } else if (builder.length() == length - 1) {
                if (Character.isWhitespace(c) ||
                    (i == input.length() - 1) ||
                    Character.isWhitespace(input.charAt(i+1))) {
                    builder.append(c);
                } else {
                    builder.append("-");
                    returnValue.add(builder.toString());
                    builder = new StringBuilder();
                    builder.append(c);
                }
            } else {
                builder.append(c);
            }
        }
        if (builder.length() > 0)
            returnValue.add(builder.toString());
        return returnValue;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String line = null;
        while (input.hasNext()) {
            line = input.nextLine();
        }
        for (String newLine : splitLines(line, 6)) {
            System.out.println(newLine);
        }
    }
}