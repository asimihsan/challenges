package ai.heapsort;

import java.util.Comparator;
import java.util.List;

public class Heap<T> {
	
	public static class IntegerComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			if (o1 < o2)
				return -1;
			else if (o1 == o2)
				return 0;
			else
				return 1;
		}
	}
		
	public static <T> void heapsort(List<T> A, Comparator<T> comp) {
		new Heap<T>().heapsort(A, comp, A.size());
	}
	
	public static void heapsort(List<Integer> A) {
		new Heap<Integer>().heapsort(A,
								     new IntegerComparator(),
								     A.size());
	}
	
	public void heapsort(List<T> A, Comparator<T> comp, int n) {
		buildMaxHeap(A, comp, n);
		for (int i = (n-1); i >= 1; i--) {
			swap(A, 0, i);
			maxHeapify(A, comp, 0, i);
		}
	}

	public void maxHeapify(List<T> A, Comparator<T> comp, int i, int n) {
		System.out.println("maxHeapify. A: " + A + ", i: " + i + ", A[i]: " + A.get(i) + ", n: " + n);
		int largest;
		int l = getLeftChildIndex(A, i);
		int r = getRightChildIndex(A, i);
		System.out.println("l: " + l + ", r: " + r);
		if ((l < n) && (comp.compare(A.get(l), A.get(i)) > 0))
			largest = l;
		else
			largest = i;
		if ((r < n) && (comp.compare(A.get(r), A.get(largest)) > 0))
			largest = r;
		System.out.println("largest: " + largest);
		if (largest != i) {
			swap(A, i, largest);
			maxHeapify(A, comp, largest, n);
		}
	}
	
	public void buildMaxHeap(List<T> A, Comparator<T> comp, int n) {
		for (int i = ((n/2)-1); i >= 0; i--)
			maxHeapify(A, comp, i, n);
	}
		
	private int getLeftChildIndex(List<T> A, int i) { return (2*i+1);  }
	private int getRightChildIndex(List<T> A, int i) { return (2*(i+1)); }
	private void swap(List<T> A, int i, int j) {
		T temp = A.get(i);
		A.set(i, A.get(j));
		A.set(j, temp);
	}
}
