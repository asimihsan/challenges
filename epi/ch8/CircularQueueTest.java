/**
 * EPI - Q8.10 - Circular queue that dynamically resizes and supports
 * O(1) enqueue and dequeue.
 */

import java.util.Arrays;
import java.util.NoSuchElementException;

class CircularQueue {
    private int count = 0;
    private int start = 0;
    private int end = 0;
    private int[] data;

    CircularQueue(int capacity) {
        data = new int[capacity];
    }

    public int size() {
        return count;
    }

    public int dequeue() {
        //debug();
        if (size() == 0)
            throw new NoSuchElementException();
        count--;
        int returnValue = data[start];
        start = (start + 1) % data.length;
        //debug();
        return returnValue;
    }

    public void enqueue(int element) {
        //debug();
        if (size() == data.length)
            grow();
        count++;
        data[end] = element;
        end = (end + 1) % data.length;
        //debug();
    }

    private void grow() {
        int[] dataNew = new int[data.length * 2];
        int cnt = 0;
        while(size() != 0) {
            dataNew[cnt++] = dequeue();
        }
        start = 0;
        end = data.length;
        count = data.length;
        data = dataNew;
    }

    private void debug() {
        System.out.println(String.format("count=%s, start=%s, end=%s, data=%s",
                                         count, start, end, Arrays.toString(data)));
    }
}

class CircularQueueTest {
    public static void main(String[] args) {
        CircularQueue q = new CircularQueue(3);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        q.enqueue(6);
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        q.enqueue(7);
        q.enqueue(8);
        q.enqueue(9);
        q.enqueue(10);
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
    }
}