class SortedArrayBinarySearch {
public static int binarySearch(int[] array, int value) {
    int lo = 0;
    int hi = array.length - 1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (array[mid] > value)
            hi = mid - 1;
        else if (array[mid] < value)
            lo = mid + 1;
        else
            return mid;
    }
    return -1;
}

    public static void main(String[] args) {
        int[] array = {-10, -2, 1, 5, 5, 8, 20, 21};
        System.out.println(binarySearch(array, 5));
        System.out.println(binarySearch(array, 10));
    }
}