class SearchInSparseStringArray {
    public static int find(String[] array, String value) {
        int lo = 0, hi = array.length - 1;
        while (hi >= lo) {
            if (array[hi].compareTo("") == 0)
                hi--;
            else
                break;
        }
        while (lo <= hi) {
            if (array[lo].compareTo("") == 0)
                lo++;
            else
                break;
        }
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            while (array[mid].compareTo("") == 0)
                mid++;
            if (mid < lo || mid > hi)
                return -1;
            if (array[mid].compareTo(value) < 0)
                lo = mid + 1;
            else if (array[mid].compareTo(value) > 0)
                hi = mid - 1;
            else
                return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        String[] array = {"at", "", "", "" , "ball", "", "", "car", "", "",
                          "dad", "", ""};
        System.out.println(find(array, "ball"));
        System.out.println(find(array, "ballcar"));
        System.out.println(find(array, "dad"));
        System.out.println(find(array, "dad2"));
    }
}