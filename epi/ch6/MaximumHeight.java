/**
 * Given an array of ints return the maximum higher between two i, j
 * where i < j.
 *
 * For example, in [1, 3, 0, 5, 6, 2, 5, 10] the answer is 10.
 *
 * - What is the naive O(n^2) time solution? Just look over pairs.
 * - What is a better O(n log n) time solution? Divide and conquer,
 *   noting that you need the smallest element on the left and the
 *   largest on the right.
 * - What is the best O(n) time solution? Similar to Kadan's algorithm,
 *   keep track of the minimum so far and update max_height considering
 *   the current element and the minimum so far.
 */

import java.util.Arrays;
import java.util.List;

class MaximumHeight {
    /**
     * Naive O(n^2) solution.
     * @param  array Input array.
     * @return       int, maximum height.
     */
    public static int solve1(List<Integer> list) {
        int maxHeight = Integer.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                maxHeight = Math.max(maxHeight, list.get(j) - list.get(i));
            }
        }
        return maxHeight;
    }

    /**
     * Best O(n) solution.
     * @param  list Input list.
     * @return       int, maximum height.
     */
    public static int solve3(List<Integer> list) {
        Integer maxHeight = Integer.MIN_VALUE;
        Integer minSoFar = Integer.MAX_VALUE;
        for (Integer item : list) {
            minSoFar = Math.min(minSoFar, item);
            maxHeight = Math.max(maxHeight, item - minSoFar);
        }
        return maxHeight;
    }

    public static void main(String[] args) {
        Integer[] array = {1, 3, 0, 5, 6, 2, 5, 10};
        List<Integer> list = Arrays.asList(array);
        System.out.println(solve1(list));
        System.out.println(solve3(list));
    }
}