/**
 * Given a sorted array find the minimum amount of values that are
 * needed to be inserted to make a sequence of a given length. ie
 * [0, 2, 5, 7, 11] for length 5
 */

class SortedArrayFindMinimumInserts {
    public static int binarySearch(int[] array, int key) {
        int lo = 0, hi = array.length - 1, mid;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;
            if (array[mid] < key)
                lo = mid + 1;
            else if (array[mid] > key)
                hi = mid - 1;
            else
                return mid;
        }
        return lo;
    }

    public static int findMinimumInserts(int[] array, int length) {
        int minimumInserts = Integer.MAX_VALUE;
        String minimumRange = null;

        int minimum = array[0];
        int maximum = array[array.length - 1];
        for (int start = minimum - length + 1, end = start + length;
             start <= maximum; start++, end++) {
            int startIndex = binarySearch(array, start);
            int endIndex = binarySearch(array, end);
            int newInserts = length - (endIndex - startIndex);
            if (newInserts < minimumInserts) {
                minimumInserts = newInserts;
                minimumRange = String.format("[%s, %s]", start, end);
            }
        }
        System.out.println(String.format("Minimum inserts: %s, for range: %s",
                                         minimumInserts, minimumRange));
        return minimumInserts;
    }

    public static void main(String[] args) {
        int[] array = {0, 2, 5, 7, 11};
        System.out.println(findMinimumInserts(array, 5));
    }
}
