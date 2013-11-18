import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class StringLengthComparator implements Comparator<String> {
    public int compare(String o1, String o2) {
        if (o1.length() < o2.length())
            return -1;
        else if (o1.length() == o2.length())
            return 0;
        else
            return 1;
    }
}

class StringsByLength {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<String>();
        strings.addAll(Arrays.asList(new String[]
            {"foobar",
             "elephant",
             "dingo",
             "1"}
        ));

        // sort by string length!
        Collections.sort(strings, new StringLengthComparator());
        System.out.println(strings);
    }
}