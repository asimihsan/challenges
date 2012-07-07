package ai.codejam;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayDeque;

public class AlienLanguage {
	private static List<String> readFile(String inputFilepath) throws IOException {
		FileReader fr;
		BufferedReader br;
		
		File f = new File(inputFilepath);
		if (!(f.isFile())) {
			throw new IllegalArgumentException("file " + inputFilepath + " doesn't exist");
		}
		try {
			fr = new FileReader(inputFilepath);
		} catch (IOException e) {
			throw new IllegalStateException("couldn't open file.");
		}
		br = new BufferedReader(fr);
		try {
			List<String> rv = new ArrayList<String>();
			String line;
			while ((line = (br.readLine())) != null) {
				rv.add(line);
			}
			return rv;
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				throw new IllegalStateException("couldn't close file.");
			}
		}		
	}
	
	private static long calculateCombinations(String test,
											  Set<String> wordsAndPrefixes) {
		ArrayDeque<Character> queue = new ArrayDeque<Character>();
		return calculateCombinations(test,
									 wordsAndPrefixes,
									 queue,
									 0);
	}
	
	private static long calculateCombinations(String test,
											  Set<String> wordsAndPrefixes,
											  ArrayDeque<Character> queue,
											  long acc) {
		//System.out.println("test: " + test + ", queue: " + queue + ", acc: " + acc);
		
		
		// Recursion - base case.
		if (test.length() == 0) {
			String candidate = queueToString(queue);
			//System.out.println("candidate: " + candidate);
			if (wordsAndPrefixes.contains(candidate)) {
				acc += 1;
			}
			return acc;			
		}
		
		// Recursion = branch pruning.
		// If our current permutation doesn't match a known
		// prefix then no point continuing.
		if (!(wordsAndPrefixes.contains(queueToString(queue))))
			return acc;
		
		if (test.charAt(0) == '(') {
			int matching = test.indexOf(')');
			String subElems = test.substring(1, matching);
			String rest = test.substring(matching+1);
			for (int i = 0; i < subElems.length(); i++) {
				char c = subElems.charAt(i);
				queue.addLast(c);
				acc = calculateCombinations(rest,
										    wordsAndPrefixes,
										    queue,
										    acc);
				queue.removeLast();
			}
			return acc;
		} else {
			queue.addLast(test.charAt(0));
			return calculateCombinations(test.substring(1),
					         			 wordsAndPrefixes,
										 queue,
										 acc);
		}
	}
	
	private static String queueToString(ArrayDeque<Character> queue) {
		StringBuilder rv = new StringBuilder();
		for (Character c : queue)
			rv.append(c);
		return rv.toString();
	}
	
	public static void main(String[] args) throws IOException {
		String inputFilepath = "A-large-practice.in";
		String outputFilepath = "A-large-practice.out";
		List<String> lines = readFile(inputFilepath);
		String[] header = lines.get(0).split("\\s");
		Integer L = Integer.valueOf(header[0]);
		Integer D = Integer.valueOf(header[1]);
		Integer N = Integer.valueOf(header[2]);
		
		Set<String> wordsAndPrefixes = new HashSet<String>(D * (L / 2));
		wordsAndPrefixes.add("");
		for (String line : lines.subList(1, D+1)) {
			for (int i = line.length() - 1; i >= 0; i--) {
				wordsAndPrefixes.add(line.substring(0, i+1));
			}
		}
		//System.out.println("wordsAndPrefixes: " + wordsAndPrefixes);
		
		List<String> output = new ArrayList<String>(N);
		int i = 1;
		for (String line : lines.subList(D+1, lines.size())) {
			long result = calculateCombinations(line, wordsAndPrefixes);
			output.add("Case #" + i + ": " + result);
			i += 1;
		}
		
		FileWriter fw = new FileWriter(outputFilepath);
		BufferedWriter bw = new BufferedWriter(fw);
		for (String line : output)
			bw.write(line + "\n");
		bw.close();
		fw.close();
		
	}
}
