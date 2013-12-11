class FirstLarger {
    static int firstLarger(int[] array, int value) {
        int lo = 0, hi = array.length - 1, mid;
        value += 1;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;
            if (array[mid] < value)
                lo = mid + 1;
            else if (array[mid] > value)
                hi = mid - 1;
            else
                return mid;
        }
        if (lo >= array.length)
            return -1;
        return lo;
    }

    public static void main(String[] args) {
        int[] array = {-14, -10, 2, 108, 108, 243, 285, 285, 285, 401};
        System.out.println(firstLarger(array, 500));
        System.out.println(firstLarger(array, 101));
        System.out.println(firstLarger(array, 108));
    }
}