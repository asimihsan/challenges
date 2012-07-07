import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ReverseWords {
	public static void main(String[] args) throws IOException {
		String inputFilepath = "/Users/ai/Programming/javakata/codejam/reverse_words/src/B-large-practice.in";
		String outputFilepath = "/Users/ai/Programming/javakata/codejam/reverse_words/src/B-large-practice.out";
		List<String> lines;
		try {
			lines = Files.readLines(new File(inputFilepath), Charsets.UTF_8);
		} catch (IOException e) {
			throw new IllegalStateException("can't read input: " + e);
		}
		int i = 1;
		FileWriter fw;
		try {
			fw = new FileWriter(outputFilepath);
		} catch (IOException e) {
			throw new IllegalStateException("can't write to file: " + e);
		}
		BufferedWriter bw = new BufferedWriter(fw);
		for (String line: lines.subList(1, lines.size())) {
			List<String> elems = Arrays.asList(line.split("\\s"));
			Collections.reverse(elems);
			StringBuilder outputLine = new StringBuilder();
			outputLine.append("Case #" + i + ":");
			for (String elem: elems)
				outputLine.append(" " + elem);
			bw.write(outputLine.toString() + "\n");

			i += 1;
		}
		bw.close();
		fw.close();

	}
}
