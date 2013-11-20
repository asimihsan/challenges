class GlassdoorTrimExtraSpaces {
    public static String trim(String input) {
        StringBuilder output = new StringBuilder();
        boolean inSpace = false;
        for (int i = 0; i < input.length(); i++) {
            int current = input.codePointAt(i);
            if (Character.isWhitespace(current) && inSpace == false) {
                output.appendCodePoint(current);
                inSpace = true;
            } else if (Character.isWhitespace(current)) {
                continue;
            } else {
                inSpace = false;
                output.appendCodePoint(current);
            }
        }
        return output.toString();
    }

    public static void main(String[] args) {
        String input = "i am  a     delicious      mmmmm string!";
        System.out.println(trim(input));
    }
}