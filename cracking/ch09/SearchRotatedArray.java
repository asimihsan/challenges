class SearchRotatedArray {
    public static int find(int[] array, int key) {
        int lo = 0;
        int hi = array.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            //System.out.println(String.format("lo: %s, mid: %s, hi: %s", lo, mid, hi));
            if (array[mid] == key)
                return mid;
            if ((array[mid] < array[0]) && (array[mid] < array[array.length - 1])) {
                // in right increasing portion of rotated array
                //System.out.println("right increasing portion");
                if ((key > array[mid]) && (key <= array[array.length - 1])) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            } else {
                // in left increasing portion of rotated array
                //System.out.println("left increasing portion"); 
                if ((key < array[mid]) && (key >= array[0])) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {11, 12, 15, 1, 5, 10};
        System.out.println(find(array, 5));  // 4
        System.out.println(find(array, 11));  // 0
        System.out.println(find(array, 10));  // 5
        System.out.println(find(array, 2));  // -1
        System.out.println(find(array, 13));  // -1
    }
}