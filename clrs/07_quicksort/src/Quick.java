import java.util.List;
import java.util.Random;

public class Quick<T extends Comparable<T>> {
	Random generator;
	
	Quick() { this(new Random()); }
	Quick(Random generator) {
		this.generator = generator;
	}
	
	static <T extends Comparable<T>> void sort(List<T> A) {
		new Quick<T>().quicksort(A, 0, A.size() - 1);
	}
	
	static <T extends Comparable<T>> void randomizedSort(List<T> A) {
		new Quick<T>().randomizedQuicksort(A, 0, A.size() - 1);
	}
	
	public int partition(List<T> A, int p, int r) {
		T x = A.get(r);  					 // pivot value
		int i = p - 1;						 // where we start comparisons to pivot.
		for (int j = p; j <= (r - 1); j++) { // from first element to before the pivot
			if (A.get(j).compareTo(x) < 0) { // is this smaller than the pivot?
				i += 1;
				exchange(A, i, j);
			}
		}
		// i+1 is the new pivot
		exchange(A, i+1, r);
		return (i+1);
	}
	
	public int randomizedPartition(List<T> A, int p, int r) {
		int i = generator.nextInt(r-p+1) + p;
		exchange(A, r, i);
		return partition(A, p, r);
	}
	
	public void randomizedQuicksort(List<T> A, int p, int r) { 
		if (p < r) {
			int q = randomizedPartition(A, p, r);
			randomizedQuicksort(A, p, q - 1);
			randomizedQuicksort(A, q + 1, r);
		}
	}
	
	public void quicksort(List<T> A, int p, int r) {
		if (p < r) {
			int q = partition(A, p, r);
			quicksort(A, p, q-1);
			quicksort(A, q+1, r);
		}
	}
	
	private void exchange(List<T> A, int i, int j) {
		T temp = A.get(i);
		A.set(i, A.get(j));
		A.set(j, temp);
	}
}
