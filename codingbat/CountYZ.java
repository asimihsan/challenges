/** Given a string, count the number of words ending in 'y' or 'z' --
 *  so the 'y' in "heavy" and the 'z' in "fez" count, but not the 'y' in
 *  "yellow" (not case sensitive). We'll say that a y or z is at the end
 *  of a word if there is not an alphabetic letter immediately following
 *  it. (Note: Character.isLetter(char) tests if a char is an alphabetic
 *  letter.)
 *  
 *  countYZ("fez day") -> 2
 *  countYZ("day fez") -> 2
 *  countYZ("day fyyyz") -> 2
 */

import java.util.regex.*;

class CountYZ {

    public static int countYZ(String str) {
        Pattern pattern = Pattern.compile("(?:y|z)(?:[^a-z]|\\Z)",
                                          Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        int count = 0;
        while (matcher.find())
            count++;
        return count;
    }

    public static void main(String[] args) {
        System.out.println(countYZ("fez day"));
        System.out.println(countYZ("day fez"));
        System.out.println(countYZ("day fyyyz"));
    }
}