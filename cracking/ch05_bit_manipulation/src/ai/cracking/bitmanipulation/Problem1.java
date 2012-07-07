package ai.cracking.bitmanipulation;

public class Problem1 {
	public static int applySubstring(long N, long M, int i, int j) {
		//System.out.println("N: " + N + ", M : " + M + ", i: " + i + ", j " + j);
		for (int k = i; k <= j; k++) {
			int maskM = 1 << (k - i);
			long bitM = (M & maskM) >> (k-i);
			if (bitM == 1)
				N |= (bitM << k);
			else
				N &= ~(1 << k);
		}
		return (int)N;
		
		/*
		int max = ~0;
		int left = max - ((1 << j) - 1);
		int right = ((1 << i) - 1);
		int mask = left | right;
		System.out.println("left: " + Integer.toBinaryString(left));
		System.out.println("right: " + Integer.toBinaryString(right));
		System.out.println("mask:  " + Integer.toBinaryString(mask));
		return (int)((N & mask) | (M << i));
		*/
	}
}
