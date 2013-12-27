/**
 * 10.8: Online Median
 *
 * You want to compute the running median of a sequence of numbers. The sequence
 * is presented to you in a streaming fashion - you cannot back up to read an
 * earlier value, and you need to output the median after reading in each new
 * element.
 *
 * Design an algorithm for computing the running median of a sequence. The time
 * complexity should be O(log n) per element read in, where n is the number of
 * values read in up to that element.
 *
 * The HackerRank "Median" problem is a bit harder, but the same underlying
 * problem, as this one:
 *
 * https://www.hackerrank.com/challenges/median
 *
 * Note that duplicate values are permitted.
 */

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.TreeMap;

class OnlineMedian {
    static class Node {
        static int staticId = 0;
        int id;
        Long value;
        Node next;
        Node prev;
        Node(Long value) {
            this.value = value;
            this.id = staticId++;
        }
        @Override
        public String toString() {
            return String.format("{value=%s, id=%s, prev.value=%s, next.value=%s}", value, id, prev.value, next.value);
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node n = (Node)o;
            return (n.value != null && n.value.equals(value));
        }
    }
    static Node sentinel = new Node(null);
    static Node root = sentinel;
    static {
        root.next = root;
        root.prev = root;
    }
    static TreeMap<Long, Node> map = new TreeMap<>();
    static int n = 0;
    static Node medianNode = sentinel;
    static final BigDecimal TWO = new BigDecimal(2);

    public static int size() {
        return n;
    }

    public static void add(Long number) {
        Node x = new Node(number);
        Node existing;
        if (map.containsKey(number)) {
            existing = map.get(number);
        } else {
            Long lower = map.lowerKey(number);
            existing = (lower == null) ? root : map.get(lower);
        }
        map.put(number, x);
        x.prev = existing;
        x.next = existing.next;
        x.prev.next = x;
        x.next.prev = x;
        n++;
        if (n == 1)
            medianNode = root.next;
        else if (n % 2 == 0  && number.compareTo(medianNode.value) < 0)
            medianNode = medianNode.prev;
        else if (n % 2 == 1 && number.compareTo(medianNode.value) >= 0)
            medianNode = medianNode.next;
    }

    public static boolean remove(Long number) {
        if (!map.containsKey(number)) return false;
        Node node = map.get(number);
        if (node.prev.equals(node))
            map.put(number, node.prev);
        else
            map.remove(number);
        node.next.prev = node.prev;
        node.prev.next = node.next;
        n--;
        if (n == 1)
            medianNode = root.next;
        else if (n % 2 == 0 && node == medianNode)
            medianNode = medianNode.prev;
        else if (n % 2 == 1 && node == medianNode)
            medianNode = medianNode.next;
        else if (n % 2 == 0  && number.compareTo(medianNode.value) >= 0)
            medianNode = medianNode.prev;
        else if (n % 2 == 1 && number.compareTo(medianNode.value) < 0)
            medianNode = medianNode.next;
        return true;
    }

    public static Number median() {
        //System.out.println(String.format("median entry. list: %s, n: %s, medianNode: %s", listToString(), n, medianNode));
        if (n == 0) return null;
        if (n % 2 == 1) return medianNode.value;
        BigDecimal first = new BigDecimal(medianNode.value);
        BigDecimal second = new BigDecimal(medianNode.next.value);
        return first.add(second).divide(TWO);
    }

    public static String listToString() {
        StringBuilder output = new StringBuilder("[");
        Node current = root.next;
        while (current != sentinel) {
            output.append(current);
            if (current.next != sentinel)
                output.append(", ");
            current = current.next;
        }
        output.append("]");
        return output.toString();
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        for (int i = 0; i < N; i++) {
            String op = in.next();
            Long number = in.nextLong();
            //System.out.println(String.format("list before: %s, op: %s, number: %s", listToString(), op, number));
            //System.out.println(String.format("map before: %s", map));
            if (op.equals("a")) {
                add(number);
                System.out.println(median());
            } else {
                boolean rc = remove(number);
                if (!rc || median() == null)
                    System.out.println("Wrong!");
                else
                    System.out.println(median());
            }
            //System.out.println(String.format("list after: %s", listToString()));
            //System.out.println(String.format("map after: %s", map));
        }
    }
}