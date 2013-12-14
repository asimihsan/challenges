/**
 * Challenge Description:
 * 
 * Many persons are familiar with the Roman numerals for relatively small
 * numbers. The symbols I (capital i), V, X, L, C, D, and M represent the
 * decimal values 1, 5, 10, 50, 100, 500 and 1000 respectively. To represent
 * other values, these symbols, and multiples where necessary, are
 * concatenated, with the smaller-valued symbols written further to the right.
 * For example, the number 3 is represented as III, and the value 73 is
 * represented as LXXIII. The exceptions to this rule occur for numbers having
 * units values of 4 or 9, and for tens values of 40 or 90. For these cases,
 * the Roman numeral representations are IV (4), IX (9), XL (40), and XC (90).
 * So the Roman numeral representations for 24, 39, 44, 49, and 94 are XXIV,
 * XXXIX, XLIV, XLIX, and XCIV, respectively. 
 *
 * Write a program to convert a cardinal number to a Roman numeral.
 * 
 * Input sample:
 * 
 * Your program should accept as its first argument a path to a filename.
 * Input example is the following
 * 
 * 159
 * 296
 * 3992
 * Input numbers are in range [1, 3999]
 * 
 * Output sample:
 * 
 * Print out Roman numerals.
 * 
 * CLIX
 * CCXCVI
 * MMMCMXCII
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class CodeEvalRomanNumerals {
    private static String toRoman(int input) {
        StringBuilder output = new StringBuilder();
        int remaining = input;
        while (remaining > 0) {
            if       (remaining >= 1000) { output.append("M"); remaining -= 1000; }
            else if  (remaining >= 900)  { output.append("CM"); remaining -= 900; }
            else if  (remaining >= 500)  { output.append("D"); remaining -= 500; }
            else if  (remaining >= 400)  { output.append("CD"); remaining -= 400; }
            else if  (remaining >= 100)  { output.append("C"); remaining -= 100; }
            else if  (remaining >= 90)  { output.append("XC"); remaining -= 90; }
            else if  (remaining >= 50)  { output.append("L"); remaining -= 50; }
            else if  (remaining >= 40)  { output.append("XL"); remaining -= 40; }
            else if  (remaining >= 10)  { output.append("X"); remaining -= 10; }
            else if  (remaining >= 9)  { output.append("IX"); remaining -= 9; }
            else if  (remaining >= 5)  { output.append("V"); remaining -= 5; }
            else if  (remaining >= 4)  { output.append("IV"); remaining -= 4; }
            else if  (remaining >= 1)  { output.append("I"); remaining -= 1; }
        }
        return output.toString();
    }

    public static void main (String[] args) {
        FileSystem fs = FileSystems.getDefault();
        Path path = fs.getPath(args[0]);
        try (
            BufferedReader br = Files.newBufferedReader(path, StandardCharsets.US_ASCII);
        ) {
            String line = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) continue;
                Integer input;
                try {
                    input = new Integer(line);
                } catch (NumberFormatException e) {
                    continue;
                }
                System.out.println(toRoman(input));
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
