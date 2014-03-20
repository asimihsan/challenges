import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BinarySearch {
    public static String solve(Reader input) {
        Scanner scanner = initializeScanner(input);
        List<Integer> array = getArray(scanner);
        List<Integer> values = getValues(scanner);

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            Integer index = indexOf(array, values.get(i));
            String format = i == (values.size() - 1) ? "%d" : "%d ";
            output.append(String.format(format, index));
        }
        return output.toString();
    }

    private static int indexOf(List<Integer> array, Integer value) {
        int lo = 0;
        int hi = array.size() - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (array.get(mid).equals(value))
                return mid + 1;
            else if (array.get(mid).compareTo(value) == 1)
                hi = mid - 1;
            else
                lo = mid + 1;
        }
        return -1;
    }

    private static Scanner initializeScanner(Reader input) {
        Scanner scanner = new Scanner(input);
        scanner.nextLine();
        scanner.nextLine();
        return scanner;
    }

    private static List<Integer> getArray(Scanner scanner) {
        List<Integer> array = new ArrayList<>();
        Scanner scannerArray = new Scanner(scanner.nextLine());
        while (scannerArray.hasNextInt())
            array.add(scannerArray.nextInt());
        return array;
    }

    private static List<Integer> getValues(Scanner scanner) {
        List<Integer> values = new ArrayList<>();
        Scanner valuesArray = new Scanner(scanner.nextLine());
        while (valuesArray.hasNextInt())
            values.add(valuesArray.nextInt());
        return values;
    }
}