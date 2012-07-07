package ai.mlfsr;

import java.lang.Math;

public class Mlfsr16 {
	/*
	 * 16 => x^16 + x^14 + x^13 + x^11 + 1
	 */
	private static int size = 16;
	private static int tap1 = 16;
	private static int tap2 = 14;
	private static int tap3 = 13;
	private static int tap4 = 11;
	
	private int period = 0;
	private final int maximumPeriod = (int) (Math.pow(2, size) - 2);
	private int lfsr;
	
	public Mlfsr16(int seed) {
		this.lfsr = seed;
	}
	
	public int next() {
		if (period >= maximumPeriod) {
			return -1;
		}
		int bit = ((lfsr >> (size - tap1)) ^ 
				   (lfsr >> (size - tap2)) ^
				   (lfsr >> (size - tap3)) ^
				   (lfsr >> (size - tap4))) & 1;
		lfsr = (lfsr >> 1) | (bit << 15);
		period++;
		return lfsr;
	}
	
}
