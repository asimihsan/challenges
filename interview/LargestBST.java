class Node<Integer> {
    Integer value;
    Node<Integer> left;
    Node<Integer> right;

    Node(Integer value) { this.value = value; }

    @Override
    public String toString() {
        return value.toString();
    }
}


class LargestBST {
    public static Node<Integer> largestBST(Node<Integer> root) {
        Node<Integer> largest = null;
        Integer minValue = 0, maxValue = 0, maxNodes = Integer.MIN_VALUE;
        largestBST(root, minValue, maxValue, maxNodes, largest);
        return largest;
    }

    private static int largestBST(Node<Integer> node, Integer minValue,
                                  Integer maxValue, Integer maxNodes,
                                  Node<Integer> largest) {
        if (node == null) return 0;
        boolean isBST = true;
        int leftNodes = largestBST(node.left, minValue, maxValue, maxNodes, largest);
        int currMin = (leftNodes == 0) ? node.value : minValue;
        if (leftNodes == -1 ||
            (leftNodes != 0 && node.value <= maxValue))
            isBST = false;
        int rightNodes = largestBST(node.right, minValue, maxValue, maxNodes, largest);
        int currMax = (rightNodes == 0) ? node.value : maxValue;
        if (rightNodes == -1 ||
            (rightNodes != 0 && node.value >= minValue))
            isBST = false;
        if (isBST) {
            minValue = currMin;
            maxValue = currMax;
            int totalNodes = leftNodes + rightNodes + 1;
            if (totalNodes > maxNodes) {
                System.out.println(String.format("largest: %s", node));
                maxNodes = totalNodes;
                largest = node;
            }
            return totalNodes;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        Node<Integer> a = new Node<Integer>(8);
        Node<Integer> b = new Node<Integer>(5);
        Node<Integer> c = new Node<Integer>(10);
        Node<Integer> d = new Node<Integer>(3);
        Node<Integer> e = new Node<Integer>(7);
        Node<Integer> f = new Node<Integer>(9);

        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        c.right = f;
        System.out.println(largestBST(a));

    }
}
