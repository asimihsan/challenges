import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

class Substitution {
    String from;
    String replacement;
    Substitution(String from, String replacement) {
        this.from = from;
        this.replacement = replacement;
    }
    @Override
    public String toString() {
        return String.format("{from=%s, replacement=%s}", from, replacement);
    }
}

class MarkedLinkedList {
    class Node {
        Node next;
        boolean marked = false;
        char value;

        Node() { }
        Node(char value) { this.value = value; }
        Node(char value, boolean marked) {
            this.value = value;
            this.marked = marked;
        }

        @Override
        public String toString() {
            return String.format("{value=%s, marked=%s}", value, marked);
        }
    }

    private Node root = new Node();

    MarkedLinkedList(String input) {
        Node prev = root;
        Node current = null;
        for (int i = 0; i < input.length(); i++) {
            current = new Node(input.charAt(i));
            prev.next = current;
            prev = current;
        }
    }

    private Node find(String sequence, Node start) {
        if (start == null) return null;
        Node prev = start;
        Node current = start.next;
        while (current != null) {
            int m = 0;
            Node subnode = current;
            while (subnode != null &&
                   subnode.marked == false &&
                   subnode.value == sequence.charAt(m)) {
                if (m == (sequence.length() - 1))
                    return prev;
                m++;
                subnode = subnode.next;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    public void replace(String from, String replacement) {
        Node prev = find(from, root);
        while (prev != null) {
            Node last = prev;
            for (int i = 0; i <= from.length(); i++) {
                last = last.next;
            }
            for (int j = 0; j < replacement.length(); j++) {
                prev.next = new Node(replacement.charAt(j), true);
                prev = prev.next;
            }
            prev.next = last;
            prev = find(from, prev);
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        Node current = root.next;
        while (current != null) {
            output.append(current.value);
            current = current.next;
        }
        return output.toString();
    }
}

class StringSubstitution {
    public static String process(String input, Collection<Substitution> substitutions) {
        MarkedLinkedList data = new MarkedLinkedList(input);
        //System.out.println(data);
        //System.out.println(substitutions);
        for (Substitution substitution : substitutions) {
            data.replace(substitution.from, substitution.replacement);
            //System.out.println(data);
        }
        return data.toString();
    }

    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath(args[0]);
        try (
            BufferedReader br = Files.newBufferedReader(path,
                                                        StandardCharsets.US_ASCII);
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elems = line.split(";");
                String[] replacements = elems[1].split(",");
                Collection<Substitution> substitutions = new ArrayList<>();
                for (int i = 0; i < replacements.length - 1; i += 2)
                    substitutions.add(new Substitution(replacements[i], replacements[i+1]));
                String result = process(elems[0], substitutions);
                //System.out.println(String.format("input: %s", line));
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}