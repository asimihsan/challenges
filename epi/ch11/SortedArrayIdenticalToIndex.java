/**
 * Q11.3: Take sorted array of distinct elements A and return index i
 * such that A[i] = i, or return -1 if no such index exists.
 */

class SortedArrayIdenticalToIndex {
    public static int find(int[] A) {
        int lo = 0;
        int hi = A.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (A[mid] > mid)
                hi = mid - 1;
            else if (A[mid] < mid)
                lo = mid + 1;
            else
                return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] A = {-5, -4, 0, 3, 5};
        System.out.println(find(A));  // 3

        int[] B = {-5, -4, 0, 1, 5};  // -1
        System.out.println(find(B));

        int[] C = {};  // -1
        System.out.println(find(C));
    }
}