/**
 * Q14.4: Write a function that takes a BST T and key k and returns the
 * first entry larger than k that would appear in an inorder walk. If k
 * is absent or no key larger than k is present return null.
 *
 * The time complexity of this solution is O(log n), and the space
 * complexity is also O(log n). It requires knowledge of how to find
 * the inorder successor node in a BST. Alternatively a simpler solution
 * would be to just go inorder, O(n) time and O(1) space. In order to
 * qualify for the O(1) space though you would have to avoid recursion.
 *
 * The book solution uses O(log n) time and O(1) space. It works
 * like this solution except rather than explicitly keeping track of
 * a path every time you go left you update a pointer to that node.
 * It's worth revisiting this problem and re-attempting the book's
 * way.
 */

import java.util.ArrayList;
import java.util.List;

class Node {
    Integer value;
    Node left;
    Node right;

    Node(Integer value) {
        this.value = value;
    }
}

class HigherKey {
    public static Integer inOrderSuccessor(Node current, List<Node> path, Integer key) {
        if (current.right != null) {
            Node leftmost = current.right;
            while (leftmost != null && leftmost.left != null) {
                leftmost = leftmost.left;
            }
            return leftmost.value;
        }
        for (int j = path.size() - 1, i = j - 1; i >= 0; i--, j--) {
            Node ni = path.get(i);
            Node nj = path.get(j);
            if (ni.left == nj)
                return ni.value;
        }
        return null;
    }

    public static Integer higherKey(Node tree, Integer key) {
        if (tree == null)
            return null;
        List<Node> path = new ArrayList<Node>();
        Node current = tree;
        while (current != null) {
            if (key < current.value) {
                path.add(current);
                current = current.left;
            } else if (key > current.value) {
                path.add(current);
                current = current.right;
            } else {
                return inOrderSuccessor(current, path, key);
            }
        }
        return null;
    }

    public static void main(String[] args) {
/**
  *                 20
  *              .-'  `-.
  *          _.-'        `-.
  *        10               30
  *       /  \                \
  *      /    \                \
  *     5      12               50
  *    / \                     /
  *   /   \                   /
  *  3     6                40
  *                        /  \
  *                       /    \
  *                     35      45
  */
        Node a = new Node(20);
        Node b = new Node(10);
        Node c = new Node(30);
        Node d = new Node(5);
        Node e = new Node(12);
        Node f = new Node(50);
        Node g = new Node(3);
        Node h = new Node(6);
        Node i = new Node(40);
        Node j = new Node(35);
        Node k = new Node(45);

        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        d.left = g;
        d.right = h;
        c.right = f;
        f.left = i;
        i.left = j;
        i.right = k;

        System.out.println(higherKey(a, 10));  // 12
        System.out.println(higherKey(a, 12));  // 20
        System.out.println(higherKey(a, 1));  // null
        System.out.println(higherKey(a, 50));  // null
        System.out.println(higherKey(a, 6));  // 10
    }
}
