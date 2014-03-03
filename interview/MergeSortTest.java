import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

class Merge {
    public static <T extends Comparable<? super T>> void sort(List<T> array) {
        List<T> buffer = new ArrayList<T>(array.size());
        for (int i = 0; i < array.size(); i++)
            buffer.add(null);
        sortHelper(array, 0, array.size() - 1, buffer);
    }

    private static <T extends Comparable<? super T>> void sortHelper(
        List<T> array, int lo, int hi, List<T> buffer) {
        if (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            sortHelper(array, lo, mid, buffer);
            sortHelper(array, mid + 1, hi, buffer);
            merge(array, lo, mid, hi, buffer);
        }
    }

    private static <T extends Comparable<? super T>> void merge(
        List<T> array, int lo, int mid, int hi, List<T> buffer) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                buffer.set(k, array.get(j++));
            else if (j > hi)
                buffer.set(k, array.get(i++));
            else if (array.get(i).compareTo(array.get(j)) == -1)
                buffer.set(k, array.get(i++));
            else
                buffer.set(k, array.get(j++));
        }
        for (int k = lo; k <= hi; k++) {
            array.set(k, buffer.get(k));
        }
    }
}

class MergeSortTest {
    public static void main(String args[]) {
        List<Integer> array = Arrays.asList(1, 7, 8, 1, 3, 1, 1, 4);
        Merge.sort(array);
        System.out.println(array);
    }
}