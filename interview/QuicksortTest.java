import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Quicksort {
    static <T extends Comparable<T>> void sort(List<T> array) {
        Collections.shuffle(array);
        sort(array, 0, array.size() - 1);
    }

    private static <T extends Comparable<T>> void sort(List<T> array, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(array, lo, hi);
        sort(array, lo, j-1);
        sort(array, j+1, hi);
    }

    private static <T extends Comparable<T>> int partition(List<T> array, int lo, int hi) {
        int i = lo, j = hi + 1;
        T pivot = array.get(lo);
        while (true) {
            while (array.get(++i).compareTo(pivot) < 0) if (i == hi) break;
            while (array.get(--j).compareTo(pivot) > 0) if (j == lo) break;
            if (i >= j) break;
            Collections.swap(array, i, j);
        }
        Collections.swap(array, lo, j);
        return j;
    }
}

class QuicksortTest {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(2, -1, 5, 3, 0, 2, 9);
        System.out.println(list);
        Quicksort.sort(list);
        System.out.println(list);
    }
}