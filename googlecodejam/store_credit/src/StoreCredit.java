import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Collections;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StoreCredit {
	static int INSERTION_SORT_THRESHOLD = 8;
	
	static <E extends Comparable<E>> void mergeSort(List<E> inputs) {
		int size = (int)(inputs.size() / ((double) 2));
		ArrayDeque<E> left = new ArrayDeque<E>(size);
		ArrayDeque<E> right = new ArrayDeque<E>(size);
		mergeSort(inputs, 
				  0,
				  inputs.size() - 1,
				  left,
				  right);
	}
	
	static <E extends Comparable<E>> void insertionSort(List<E> inputs,
														final int p,
														final int r) {
		for (int i = p + 1; i <= r; i++)
			insert(inputs, p, i, inputs.get(i));
	}
	
	static <E extends Comparable<E>> void insert(List<E> inputs,
												 final int p,
											     final int pos,
											     E value) {
		int i = pos - 1;
		while ((i >= p) && (inputs.get(i).compareTo(value) > 0)) {
			inputs.set(i+1, inputs.get(i));
			i -= 1;
		}
		inputs.set(i+1, value);
	}
	
	static <E extends Comparable<E>> void mergeSort(List<E> inputs,
										 			final int p,
													final int r,
													ArrayDeque<E> left,
													ArrayDeque<E> right) {
		if ((r - p) <= INSERTION_SORT_THRESHOLD)
			insertionSort(inputs, p, r);
		else if (p < r) {
			int q = ((r - p) / 2) + p;
			mergeSort(inputs, p, q, left, right);
			mergeSort(inputs, q + 1, r, left, right);
			merge(inputs, p, q, r, left, right);
		}
	}
	
	static <E extends Comparable<E>> void merge(List<E> inputs,
											    final int p,
											    final int q,
											    final int r,
											    ArrayDeque<E> left,
											    ArrayDeque<E> right) {
		// [r, q] is sorted. [q+1, p] is sorted. Push into two
		// lists, then dequeue in in order from r to p.
		left.clear();
		for (int i = q; i >= p; i--)
			left.addFirst(inputs.get(i));
		right.clear();
		for (int i = r; i >= (q+1); i--)
			right.addFirst(inputs.get(i));
		for (int i = p; i <= r; i++) {
			if (left.size() == 0)
				inputs.set(i, right.removeFirst());
			else if (right.size() == 0)
				inputs.set(i, left.removeFirst());
			else
				if (left.getFirst().compareTo(right.getFirst()) < 0)
					inputs.set(i, left.removeFirst());
				else
					inputs.set(i, right.removeFirst());
		} // dequeue
	} // merge
	
	static List<Integer> lineToInts(String line) {
		String[] elements = line.split("\\s");
		List<Integer> elementsAsInts = new ArrayList<Integer>(elements.length);
		for (String element : elements)
			elementsAsInts.add(Integer.parseInt(element));
		return elementsAsInts;
	}
	
	static List<Integer> lineToSortedInts(String line) {
		List<Integer> elementsSorted = lineToInts(line);
		mergeSort(elementsSorted);
		return elementsSorted;
	}
	
	static List<String> convertFileToOutput(String filepath) throws IOException {
		FileReader fileReader = new FileReader(filepath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		String firstLine = bufferedReader.readLine();
		Integer numberOfProblems = Integer.valueOf(firstLine);
		List<String> rv = new ArrayList<String>(numberOfProblems);
		for (int i = 0; i < numberOfProblems; i++) {
			int credit = Integer.valueOf(bufferedReader.readLine());
			bufferedReader.readLine();
			String items = bufferedReader.readLine();
			Problem problem = new Problem(credit, items);
			List<Integer> solution = problem.solveProblem();
			StringBuilder output = new StringBuilder();
			output.append("Case #");
			output.append(i+1);
			output.append(":");
			for (Integer index: solution) {
				output.append(" ");
				output.append(index);
			}
			rv.add(output.toString());
		}
		bufferedReader.close();
		return rv;
	}
	
	public static class Problem {
		private int credit;
		private List<Integer> items;
		private List<Integer> itemsSorted;
		private int numberOfItems;
		Problem(int credit, String items) {
			this.credit = credit;
			this.items = lineToInts(items);
			this.itemsSorted = lineToSortedInts(items);
			this.numberOfItems = itemsSorted.size();
		}
		
		List<Integer> solveProblem() {
			// Binary chop, find first pair of integers that sum to <= credit.
			int l = 1;
			int h = numberOfItems - 1;
			int i = -1;
			while (l <= h) {
				i = (l + h) / 2;
				int sum = itemsSorted.get(i) + itemsSorted.get(i-1);
				if       (sum == credit) break;
				else if  (sum > credit)  h = i - 1;
				else                     l = i + 1;
			}
			if (itemsSorted.get(i) + itemsSorted.get(i-1) < credit)
				i += 1;
			for (int j = i; j < numberOfItems; j++) {
				for (int k = (j-1); k >= 0; k--) {
					int sum = itemsSorted.get(j) + itemsSorted.get(k);
					if (sum == credit) {
						int indexK = items.indexOf(itemsSorted.get(k));
						int indexJ = items.indexOf(itemsSorted.get(j));
						if (indexJ == indexK)
							indexJ = items.subList(j+1, numberOfItems).indexOf(itemsSorted.get(j)) + (j+1);
						if (indexK < indexJ)
							return Arrays.asList(indexK+1, indexJ+1);
						else
							return Arrays.asList(indexJ+1, indexK+1);
					} else if (sum < credit) {
						break;
					}
				}
			}
			System.out.println("whoops!");
			return Collections.emptyList();
		}
	}
}
