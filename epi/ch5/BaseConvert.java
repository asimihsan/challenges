/**
 * Q5.7 - Given an input string and an input base b_1 convert the
 * number to the target base b_2 and return it as a string.
 *
 * b_1 and b_2 are [2, 16] inclusive. A = 10, B = 11, ...
 */

import java.util.ArrayList;
import java.util.List;


class BaseConvert {
    public static String convert(String input, int baseInput, int baseOutput) {
        int sum = 0;
        for (int i = input.length() - 1, power = 0; i >= 0; i--, power++) {
            char c = input.charAt(i);
            int multiple = (int)Math.pow(baseInput, power);
            if (c - '0' <= 9)
                sum += (c - '0') * multiple;
            else
                sum += (c - 'A' + 10) * multiple;   
        }
        List<Integer> values = new ArrayList<Integer>();
        while (sum != 0)
        {
            int remainder = sum % baseOutput;
            sum = sum / baseOutput;
            values.add(remainder);
        }
        StringBuilder output = new StringBuilder();
        for (Integer value : values) {
            if (value <= 9) 
                output.append(new Character((char)(value + '0')));
            else
                output.append(new Character((char)(value - 10 + 'A')));
        }
        return output.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(convert("10110100", 2, 16)); // B4
        System.out.println(convert("B4", 16, 2));  // 10110100
    }
}