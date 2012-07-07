import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Anagrams {
	static String DICT_LOCATION = "/usr/share/dict/words";
	private Map<String, List<String>> signatureToWord;

	public List<String> getAnagrams(String word) {
		String signature = getSignature(word);
		//System.out.println("word: " + word + ", signature: " + signature);
		if (!signatureToWord.containsKey(signature)) {
			return null;
		}
		List<String> anagrams = signatureToWord.get(signature);
		//System.out.println("anagrams: " + anagrams);
		
		// return a defensive copy.
		return new ArrayList<String>(anagrams);
	}
	
	Anagrams() {
		try {
			populateWords();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}
	
	private void populateWords() throws IOException {
		InputStream fis;
		BufferedReader br;
		fis = new FileInputStream(DICT_LOCATION);
		br = new BufferedReader(new InputStreamReader(fis,
									                  Charset.forName("UTF-8")));
		signatureToWord = new HashMap<String, List<String>>();
		String line;
		while ((line = br.readLine()) != null) {
			String word = line.trim();
			String signature = getSignature(word);
			if (!signatureToWord.containsKey(signature)) {
				signatureToWord.put(signature, new LinkedList<String>());
			}
			signatureToWord.get(signature).add(word);
		}
	}
	
	private String getSignature(String word) {
		List<Character> chars = new ArrayList<Character>(word.length());
		for (int i = 0; i < word.length(); i++)
			chars.add(word.charAt(i));
		Collections.sort(chars);
		StringBuilder rv = new StringBuilder();
		for (int i = 0; i < word.length(); i++)
			rv.append(chars.get(i));
		return rv.toString();
	}

}
