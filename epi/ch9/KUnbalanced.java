/**
 * Q9.2: a node in a binary tree is k-balanced if the absolute
 * difference between the number of descendants in the left and
 * right subtrees is less than or equal to k.
 * 
 * Find any k-unbalanced node in a binary tree, or return null
 * if no such node exists.
 */

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

class Pair<F, S> {
    final F first;
    final S second;
    Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}

class KUnbalanced {
    static Node found = null;

    public static Node findUnbalanced(Node root, int k) {    
        findUnbalancedHelper(root, k);
        return found;
    }

    private static Pair<Integer, Boolean> findUnbalancedHelper(Node node, int k) {
        System.out.println(String.format("node=%s, found=%s", node, found));
        if (found != null)
            return null;
        int cntLeft = 0, cntRight = 0;
        boolean isBalancedLeft = true, isBalancedRight = true;
        if (node.left != null) {
            Pair<Integer, Boolean> returnLeft = findUnbalancedHelper(node.left, k);
            if (returnLeft == null)
                return null;
            cntLeft = returnLeft.first;
            isBalancedLeft = returnLeft.second;
        }
        if (node.right != null) {
            Pair<Integer, Boolean> returnRight = findUnbalancedHelper(node.right, k);
            if (returnRight == null)
                return null;
            cntRight = returnRight.first;
            isBalancedRight = returnRight.second;
        }
        System.out.println(String.format("node=%s, cntLeft=%s, cntRight=%s, isBalancedLeft=%s, isBalancedRight=%s, found=%s", node, cntLeft, cntRight, isBalancedLeft, isBalancedRight, found));
        if (Math.abs(cntLeft - cntRight) <= k) {
            // we are k-balanced
            return new Pair<Integer, Boolean>(cntLeft + cntRight + 1, true);
        } else if (isBalancedLeft && isBalancedRight) {
            // we are k-unbalanced, our children are both k-balanced
            found = node;
        }
        // we are k-unbalanced, either/both children are k-unbalanced
        return new Pair<Integer, Boolean>(cntLeft + cntRight + 1, false);
    }

    public static void main(String[] args) {
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        Node d = new Node(4);
        Node e = new Node(5);
        Node f = new Node(6);
        Node g = new Node(7);
        Node h = new Node(8);

        a.left = b;
        a.right = c;
        b.left = d;
        b.right = f;
        c.right = h;
        d.left = e;
        e.left = f;
        f.left = g;

        System.out.println(findUnbalanced(a, 1));
    }
}