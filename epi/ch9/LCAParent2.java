/**
 * EPI, Q9.14
 * 
 * Given binary tree with n nodes and height h where nodes have parent pointers
 * get LCA of two nodes in O(max(d_a - d_l, d_b - d_l)) time, where:
 *
 * - d_a and d_b are the depths of nodes a and b.
 * - d_l is the depth of the lowest common ancestor (LCA).
 */

import java.util.HashSet;
import java.util.Set;

class Node {
    Node left;
    Node right;
    Node parent;
    String value;

    Node(String value) { this.value = value; }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Node)) return false;
      Node n = (Node)o;
      return value.equals(n.value);
    }

    @Override
    public int hashCode() {
      int result = 17;
      result = 31 * result + value.hashCode();
      return result;
    }

    @Override
    public String toString() {
        return String.format("{value=%s, left=%s, right=%s}", value, left, right);
    }
}

class NodeUtils {
    public static int height(Node node) {
        int height = 0;
        while (node.parent != null) {
            node = node.parent;
            height++;
        }
        return height;
    }

    public static Node advance(Node node, int distance) {
        while (distance > 0) {
            node = node.parent;
            distance--;
        }
        return node;
    }
}

class LCAParent2 {
    /**
     * Given Node objects that provide parent pointers in a binary tree return
     * the least common ancestor (LCA) of the two nodes.
     *
     * @param  n1 Node #1.
     * @param  n2 Node #2.
     * @return    Node of the least common ancestor (LCA) if present, else
     *            null.
     */
    public static Node LCA(Node n1, Node n2) {
        Set<Node> visited = new HashSet<>();
        while (n1 != null && n2 != null) {
            if (visited.contains(n1)) return n1;
            visited.add(n1);
            if (visited.contains(n2)) return n2;
            visited.add(n2);
            n1 = n1.parent;
            n2 = n2.parent;
        }
        return null;
    }

    public static void main(String[] args) {
        /**                                      
          *                 H
          *                 +
          *                 |
          *         B +---+-+------+ C
          *         +                +
          *         |                |
          *   F +---+--+ E           +---+ D
          *                                +
          *              +                 |
          *         A +--+                 +---+G
          *                                     +
          *                                     |
          *                                     |
          *                                I+---+
          */

        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node d = new Node("d");
        Node e = new Node("e");
        Node f = new Node("f");
        Node g = new Node("g");
        Node h = new Node("h");
        Node i = new Node("i");

        h.left = b;
        h.right = c;
        b.left = f;
        b.right = e;
        b.parent = h;
        f.parent = b;
        e.left = a;
        e.parent = b;
        a.parent = e;
        c.right = d;
        c.parent = h;
        d.right = c;
        d.parent = c;
        g.left = i;
        g.parent = d;

        System.out.println(LCA(a, f));
    }
}