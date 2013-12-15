import java.util.ArrayList;
import java.util.List;

class Node {
    Node left;
    Node right;
    String value;

    Node(String value) { this.value = value; }

    @Override
    public String toString() {
        return String.format("{value=%s, left=%s, right=%s}", value, left, right);
    }
}

class ReconstructTree {
    public static Node getTree(String[] inorder, String[] preorder) {
        Node root = new Node(preorder[0]);
        List<String> inorderList = new ArrayList<>(inorder.length);
        for (String elem : inorder) inorderList.add(elem);
        List<String> preorderList = new ArrayList<>(preorder.length);
        for (String elem : preorder) preorderList.add(elem);
        return getTree(root, inorderList, preorderList);
    }

    private static Node getTree(Node x, List<String> inorder, List<String> preorder) {
        if (inorder.size() == 0) return null;
        if (inorder.size() == 1) return x;
        int xIndex = inorder.indexOf(x.value);

        List<String> leftSubtree = inorder.subList(0, xIndex);
        String leftRootValue = leftMost(leftSubtree, preorder);
        Node left = new Node(leftRootValue);
        x.left = getTree(left, leftSubtree, preorder);

        List<String> rightSubtree = inorder.subList(xIndex + 1, inorder.size());
        String rightRootValue = leftMost(rightSubtree, preorder);
        Node right = new Node(rightRootValue);
        x.right = getTree(right, rightSubtree, preorder);

        return x;
    }

    private static String leftMost(List<String> candidates, List<String> list) {
        String returnValue = null;
        int index = Integer.MAX_VALUE;
        for (String candidate : candidates) {
            int newIndex = list.indexOf(candidate);
            if (newIndex < index) {
                index = newIndex;
                returnValue = candidate;
            }
        }
        return returnValue;
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
        String[] inorder = {"F", "B", "A", "E", "H", "C", "D", "I", "G"};
        String[] preorder = {"H", "B", "F", "E", "A", "C", "D", "G", "I"};
        System.out.println(getTree(inorder, preorder));
    }
}