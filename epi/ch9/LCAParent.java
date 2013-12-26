/**
 * EPI, Q9.13
 * 
 * Given binary tree with n nodes and height h where nodes have parent pointers
 * get LCA of two nodes in O(h) time and O(1) space.
 */

class Node {
    Node left;
    Node right;
    Node parent;
    String value;

    Node(String value) { this.value = value; }

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

class LCAParent {
    /**
     * Given Node objects that provide parent pointers in a binary tree return
     * the least common ancestor (LCA) of the two nodes.
     *
     * If the nodes were the same height (distance from root) we could just
     * advance them in lockstep (towards the root) and return the first common
     * node if present, else return null.
     *
     * However they may not be the same height. Without loss of generality,
     * however, we can advance the more distance node by the difference in
     * heights and then proceed as above.
     *
     * Note that we use reference comparisons rather than 'equals' comparisons,
     * as in general a binary tree may contain duplicate values.
     * 
     * @param  n1 Node #1.
     * @param  n2 Node #2.
     * @return    Node of the least common ancestor (LCA) if present, else
     *            null.
     */
    public static Node LCA(Node n1, Node n2) {
        int height1 = NodeUtils.height(n1);
        int height2 = NodeUtils.height(n2);
        System.out.println(String.format("height1: %s, height2: %s", height1, height2));
        if (height1 > height2)
            n1 = NodeUtils.advance(n1, height1 - height2);
        else
            n2 = NodeUtils.advance(n2, height2 - height1);
        while (n1 != null && n2 != null) {
            if (n1 == n2) return n1;
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