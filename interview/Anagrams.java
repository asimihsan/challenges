/**
 * find the sets of words that share the same characters that contain the most
 * words in them.
 */

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

class CollectionSizeComparator<T> implements Comparator<Collection<T>> {
    @Override
    public int compare(Collection<T> c1, Collection<T> c2) {
        if (c1.size() < c2.size()) return 1;
        else if (c1.size() == c2.size()) return 0;
        else return -1;
    }
}

class Anagrams {
    public static void main(String[] args) throws IOException {
        Map<String, Collection<String>> anagrams = new HashMap<>();
        Path path = FileSystems.getDefault().getPath("/usr/share/dict/words");
        try (
            BufferedReader br = Files.newBufferedReader(path,
                                                        StandardCharsets.UTF_8);
        ) {
            String line = null;
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase();
                char[] temp = line.toCharArray();
                Arrays.sort(temp);
                String sorted = new String(temp);
                if (!(anagrams.containsKey(sorted)))
                    anagrams.put(sorted, new LinkedHashSet<String>());
                anagrams.get(sorted).add(line);
            }
        }
        List<Collection<String>> sortedAnagrams =
            new ArrayList<>(anagrams.values());
        Collections.sort(sortedAnagrams,
                         new CollectionSizeComparator<String>());
        for (int i = 0; i < 10; i++)
            System.out.println(sortedAnagrams.get(i));
    }
}