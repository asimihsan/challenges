/**
Credits: This problem appeared in the Facebook Hacker Cup 2013
Hackathon.

When John was a little kid he didn't have much to do. There was no
internet, no Facebook, and no programs to hack on. So he did the only
thing he could... he evaluated the beauty of strings in a quest to
discover the most beautiful string in the world.

Given a string s, little Johnny defined the beauty of the string as the
sum of the beauty of the letters in it. The beauty of each letter is an
integer between 1 and 26, inclusive, and no two letters have the same
beauty. Johnny doesn't care about whether letters are uppercase or
lowercase, so that doesn't affect the beauty of a letter. (Uppercase 'F'
is exactly as beautiful as lowercase 'f', for example.)

You're a student writing a report on the youth of this famous hacker.
You found the string that Johnny considered most beautiful. What is the
maximum possible beauty of this string?
 */

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

class BeautifulStrings {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get(args[0]);
        try (
            BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        ) {
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(getMaximumBeauty(line.toLowerCase().trim()));
            }
        }
    }

    public static int getMaximumBeauty(String input) {
        Map<Character, Integer> counts = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            Character c = input.charAt(i);
            if (!(Character.isLetter(c)))
                continue;
            counts.put(c, counts.containsKey(c) ? counts.get(c) + 1 : 1);
        }
        Map<Character, Integer> lettersByCount = sortByValues(counts);
        int beauty = 0;
        int currentWeight = 26;
        for (Map.Entry<Character, Integer> entry : lettersByCount.entrySet()) {
            Character c = entry.getKey();
            Integer count = entry.getValue();
            beauty += currentWeight * count;
            currentWeight--;
        }
        return beauty;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValues(Map<K, V> counts) {
        // capture all the entries now. iterating over the entry set adding
        // it to a TreeMap with a custom comparator is dangerous because
        // if the underlying map changes weird stack traces.
        List<Map.Entry<K, V>> entries = new ArrayList<>(counts.entrySet());
        
        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> entry1, Map.Entry<K, V> entry2) {
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });
        Map<K, V> sortedCounts = new LinkedHashMap<>(entries.size());
        for (Map.Entry<K, V> entry : entries)
            sortedCounts.put(entry.getKey(), entry.getValue());
        return sortedCounts;
    }
}