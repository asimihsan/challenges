import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.zip.CRC32;

class WordIterable implements Iterable<String> {
    public Iterator iterator() {
        return new Iterator();
    }

    class Iterator implements java.util.Iterator<String> {
        private final Path path = FileSystems.getDefault().getPath("/usr/share/dict/words");
        private BufferedReader br = null;
        private String line = null;
        private boolean finished = false;

        Iterator() {
            super();
            try {
                br = Files.newBufferedReader(path, StandardCharsets.US_ASCII);
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace(System.err);
                finished = true;
            }
        }

        public boolean hasNext() {
            return !finished;
        }

        public String next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException();
            String returnValue = new String(line);
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace(System.err);
                finished = true;
                return returnValue;
            }
            if (line == null) {
                finished = true;
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
            return returnValue.toLowerCase();
        }

        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }
}

interface SpellChecker {
    public void addWord(String word);
    public boolean isValid(String candidate);
}

class HashSetSpellChecker implements SpellChecker {
    private Set<String> data = new HashSet<>();

    public void addWord(String word) {
        data.add(word);
    }

    public boolean isValid(String candidate) {
        return data.contains(candidate);
    }
}

class BloomFilterSpellChecker implements SpellChecker {
    private BitSet bitset;
    private int k;
    private int m;

    BloomFilterSpellChecker(int m, int k) {
        bitset = new BitSet(m);
        this.m = m;
        this.k = k;
    }

    private int[] getHashBits(String word) {
        int[] returnValue = new int[k];
        CRC32 hasher = new CRC32();
        byte[] bytes = word.getBytes();
        for (int i = 0; i < k; i++) {
            hasher.update(bytes);
            returnValue[i] = ((int)(hasher.getValue() & 0x7fffffff)) % m;
        }
        return returnValue;
    }

    public void addWord(String word) {
        for (int hashBit : getHashBits(word))
            bitset.set(hashBit % m);
    }

    public boolean isValid(String candidate) {
        for (int hashBit : getHashBits(candidate))
            if (!bitset.get(hashBit)) return false;
        return true;
    }
}

class TrieSpellChecker implements SpellChecker {
    private static final int R = 26;
    private Node root;

    class Node {
        boolean isWord = false;
        Node[] next = new Node[R];
    }

    public void addWord(String word) {
        root = put(root, word, 0);
    }

    private Node put(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) { x.isWord = true; return x; }
        char c = (char)(key.charAt(d) - 'a');
        x.next[c] = put(x.next[c], key, d + 1);
        return x;
    }

    public boolean isValid(String candidate) {
        Node x = get(root, candidate, 0);
        if (x == null || x.isWord == false) return false;
        return true;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = (char)(key.charAt(d) - 'a');
        return get(x.next[c], key, d + 1);
    }
}

class BloomFilterExperiment {
    public static void main(String[] args) {
        //SpellChecker checker = new HashSetSpellChecker();
        //SpellChecker checker = new BloomFilterSpellChecker(2_000_000, 4);
        SpellChecker checker = new TrieSpellChecker();
        for (String word : new WordIterable()) {
            if (!(word.matches("[a-z]+"))) continue;
            checker.addWord(word);
        }
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            StringBuilder output = new StringBuilder();
            for (int j = 0; j < 5; j++) {
                Character c = new Character((char)(r.nextInt(26) + 97));
                output.append(c);
            }
            String word = output.toString();
            System.out.println(String.format("word=%s, isValid=%s", word, checker.isValid(word)));
        }
        System.out.println(String.format("word=%s, isValid=%s", "hello", checker.isValid("hello")));
    }
}