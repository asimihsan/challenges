/**
 * EPI q10.6. 
 *
 * The input consists of a very long sequence of numbers. Each number is at
 * most k positions away from its correctly sorted position. Design an
 * algorithm that outputs the numbers in the correct order using O(k)
 * storage, independent of the number of elemnts processed.
 *
 * (This solution is slightly more general than the book's solution. The book
 * assumes k is known a-prioi, whereas this solution does not. The time and
 * space complexities are identical).
 */

import java.util.PriorityQueue;

/**
 * Expects a monotonically increasing sequence of T objects. If the sequence
 * is violated it starts buffering objects internally until the sequence is
 * restored. A sequence is judged restored if two in-order elements are seen.
 *
 * This data structure cannot hold null elements. Adding a null element is used
 * to signal the end of the stream.
 */
class BufferedSorter<T extends Comparable<? super T>> {
    private PriorityQueue<T> pq = new PriorityQueue<>();
    private T previous = null;
    private T current = null;
    private boolean previousConsumed = false;
    private int correctlyOrdered = 0;

    public void add(T element) {
        previousConsumed = false;
        previous = current;
        current = element;
        if (element == null)
            return;
        correctlyOrdered += 1;
        if (previous == null) {
            return;
        } else if (previous.compareTo(current) > 0) {
            correctlyOrdered = 0;
            pq.add(previous);
            pq.add(current);
        }
    }

    public T consume() {
        if (!pq.isEmpty() && correctlyOrdered >= 2) 
            return pq.poll();
        if (pq.isEmpty() && !previousConsumed) {
            previousConsumed = true;
            return previous;
        }
        return null;
    }

}

class ApproximateSort {
    public static void main(String[] args) {
        BufferedSorter<Integer> bs = new BufferedSorter<>();
        Integer[] sequence = {1, 2, 5, 3, 6, 4, 7, 8, 9, 10, null};
        Integer j;
        for (Integer i : sequence) {
            bs.add(i);
            while ((j = bs.consume()) != null)
                System.out.println(j);
        }
    }
}