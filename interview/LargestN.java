import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

class MinHeap {
    private List<Integer> data = new ArrayList<Integer>();
    public final int maxSize;

    MinHeap(int maxSize) {
        this.maxSize = maxSize;
    }

    public void push(Integer value) {
        data.add(value);
        bubbleUp(data.size() - 1);
        if (size() > maxSize)
            pop();
    }

    public Integer pop() {
        if (isEmpty())
            throw new NoSuchElementException();
        Integer returnValue = data.get(0);
        Collections.swap(data, 0, data.size() - 1);
        data.remove(data.size() - 1);
        bubbleDown(0);
        return returnValue;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return data.size();
    }

    private void bubbleUp(int i) {
        if (i == 0)
            return;
        int parentIndex = (i % 2 == 0) ? (i/2 - 1) : (i/2);
        if (data.get(parentIndex).compareTo(data.get(i)) == 1) {
            Collections.swap(data, i, parentIndex);
            bubbleUp(parentIndex);
        }
    }

    private void bubbleDown(int i) {
        int leftChildIndex = 2*i + 1;
        int rightChildIndex = 2*i + 2;
        if (leftChildIndex > size() - 1)
            return;
        int dominantChild;
        if (rightChildIndex > size() - 1) {
            dominantChild = leftChildIndex;
        } else if (data.get(leftChildIndex).compareTo(data.get(rightChildIndex)) == -1) {
            dominantChild = leftChildIndex;
        } else {
            dominantChild = rightChildIndex;
        }
        if (data.get(i).compareTo(data.get(dominantChild)) == 1) {
            Collections.swap(data, i, dominantChild);
            bubbleDown(dominantChild);
        }
    }
}

class LargestN {
    public static void main(String[] args) {
        int[] array = {1, 5, -1, 10, 4, 3, 2, 9, 5, 5, 1};
        MinHeap m = new MinHeap(3);
        for (int i : array)
            m.push(i);
        while (!(m.isEmpty()))
            System.out.println(m.pop());
    }
}