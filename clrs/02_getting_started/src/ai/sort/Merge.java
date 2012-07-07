package ai.sort;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class Merge<T> {
	public void sort(List<T> input, Comparator<T> comparator) {
		int size = input.size();
		int p = 0;
		int r = size - 1;
		List<T> temporaryLeft = new ArrayList<T>(size / 2 + 1);
		List<T> temporaryRight = new ArrayList<T>(size / 2 + 1);
		mergeSort(input, p, r, temporaryLeft, temporaryRight, comparator);
	} // static sort
	
	private void mergeSort(List<T> A,
						   int p,
						   int r,
						   List<T> temporaryLeft,
						   List<T> temporaryRight,
						   Comparator<T> comparator) {
		if (p < r) {
			int q = (p + r) / 2;
			mergeSort(A, p, q, temporaryLeft, temporaryRight, comparator);
			mergeSort(A, q+1, r, temporaryLeft, temporaryRight, comparator);
			merge(A, p, q, r, temporaryLeft, temporaryRight, comparator);
		} // if (p < r)
	} // mergeSort
	
	private void merge(List<T> A,
					   int p,
					   int q,
					   int r,
					   List<T> temporaryLeft,
					   List<T> temporaryRight,
					   Comparator<T> comparator) {
		/*
		 * [p, q] is the first sorted sublist.
		 * [q + 1, r] is the second sorted sublist.
		 * 
		 * Sublists will never be empty. Rather, e.g.
		 * q = 0, p = 0 implies the left sublist is
		 * [ A[0] ], a list of one element of the left
		 * most element of A.
		 * 
		 * We will end up with the range [p, r] sorted
		 * by merging each sublist into a temporary
		 * area and then writing them out in sequence.
		 * 
		 * We know we must perform (r - p + 1)
		 * comparisons and copies so just track this
		 * rather than using sentinels.
		 */
		temporaryLeft.clear();
		temporaryLeft.addAll(A.subList(p, q + 1));
		temporaryRight.clear();
		temporaryRight.addAll(A.subList(q + 1, r + 1));
		
		int leftCnt = 0;
		int rightCnt = 0;
		int leftMax = q - p  + 1;
		int rightMax = r - (q + 1) + 1;
		for (int i = p; i <= r; i++) {		
			if (leftCnt >= leftMax) {
				A.set(i, temporaryRight.get(rightCnt++));
				continue;
			} else if (rightCnt >= rightMax) {
				A.set(i, temporaryLeft.get(leftCnt++));
				continue;
			} else {
				T leftElem = temporaryLeft.get(leftCnt);
				T rightElem = temporaryRight.get(rightCnt);
				int comparison = comparator.compare(leftElem, rightElem);
				if (comparison <= 0) {
					A.set(i, leftElem);
					leftCnt++;
				} else {
					A.set(i, rightElem);
					rightCnt++;
				}
			}
		}
		
	} // merge
}
