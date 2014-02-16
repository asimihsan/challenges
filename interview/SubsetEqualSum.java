/**
 * Given a set A of 20 distinct positive integers, find two subsets that have the
 * same sum. For example, if A = {10, 20, 30, 40, ..., 200}, then {10, 20, 30,
 * 40, 100} and {90, 110} are two subsets that have the same sum (which is 200).
 * If there exists such a pair of subset, print out the numbers, otherwise print
 * out "none". If there are multiple such sets, you only need find and print out
 * one pair.
 *
 * This problem is NP-complete. You must look at all 2^n subsets, hence
 * the restriction on size. This problem involves correctly and
 * efficiently iterating over the powerset (set of all subsets, of
 * course except the empty set and the set itself).
 */

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class SubsetEqualSum {
    public static void incrementBitSet(BitSet bs) {
        int ncb = bs.nextClearBit(0);
        bs.set(ncb);
        bs.clear(0, ncb);
    }

    public static void equalSumSubsets(int[] array) {
        Map<Integer, List<Integer>> sums = new HashMap<>();
        BitSet bs = new BitSet(array.length);
        while (bs.nextClearBit(0) != array.length) {
            incrementBitSet(bs);

            int sum = 0;
            List<Integer> xs = new ArrayList<>();
            for (int i = bs.nextSetBit(0); i != -1; i = bs.nextSetBit(i+1)) {
                int x = array[i];
                sum += x;
                xs.add(x);
            }
            if (sums.containsKey(sum)) {
                System.out.println(String.format("sum = %s, xs1 = %s, xs2 = %s", sum, xs, sums.get(sum)));
                return;
            }
            sums.put(sum, xs);
        }
        System.out.println("none");
    }

    public static void main(String[] args) {
        int[] array1 = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110,
                        120, 130, 140, 150, 160, 170, 180, 190, 200};
        int[] array2 = {1, 3, 5, 7, 10};
        int[] array3 = {1, 5, 10, 20, 40, 80, 160, 320, 640, 1280, 2560};
        equalSumSubsets(array1);
        equalSumSubsets(array2);
        equalSumSubsets(array3);
    }
}