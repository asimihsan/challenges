/* Given a binary tree and an integer S , print all distinct paths from root to
   leaves which sum to S. Describe the algorithm and implement it.
*/

import java.util.ArrayDeque;
import java.util.Deque;

class Node {
    Node left;
    Node right;
    int value;

    Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("{value=%s}", value);
    }
}

class PathsWithSum {
    public static void pathsWithSum(Node root, int S) {
        if (root == null)
            return;
        Deque<Node> q = new ArrayDeque<Node>();
        pathsWithSumHelper(root, S, 0, q);
    }

    private static void pathsWithSumHelper(Node current, int S, int acc, Deque<Node> q) {
        assert(current != null);
        q.push(current);
        if (current.left != null)
            pathsWithSumHelper(current.left, S, acc + current.value, q);
        if (current.right != null)
            pathsWithSumHelper(current.right, S, acc + current.value, q);
        if (current.left == null && current.right == null && (acc + current.value == S))
            System.out.println(q);
        q.pop();
    }

    public static void main(String[] args) {
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        Node d = new Node(5);
        Node e = new Node(7);
        Node f = new Node(6);
        Node g = new Node(8);
        Node h = new Node(9);

        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        c.right = f;
        f.right = g;
        g.right = h;

        pathsWithSum(a, 10);
    }
}