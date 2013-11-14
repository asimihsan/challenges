import java.util.ArrayDeque;
import java.util.Queue;

class Node {
    Node left;
    Node right;
    int value;
    Node() {
    }
    Node(int value) {
        this.value = value;
    }
    @Override public String toString() {
        return String.format("{value: %s}", value);
    }
}

class Sentinel extends Node {
    @Override public String toString() {
        return "{_sentinel}";
    }
}

class DifferenceBetweenSums {
    static int differenceBetweenSums(Node node) {
        Queue<Node> queue = new ArrayDeque<Node>();
        Sentinel sentinel = new Sentinel();
        queue.add(node);
        queue.add(sentinel);
        int sumOdd = 0, sumEven = 0, level = 1, currentSum = 0;
        while(true) {
            System.out.println(queue);
            Node elem = queue.remove();
            if (elem == sentinel) {
                if (level % 2 == 0)
                    sumEven += currentSum;
                else
                    sumOdd += currentSum;
                // sentinel
                if (queue.size() == 0)
                    break;
                currentSum = 0;
                level += 1;
                queue.add(sentinel);

            } else {
                currentSum += elem.value;
                if (elem.left != null)
                    queue.add(elem.left);
                if (elem.right != null)
                    queue.add(elem.right);
            }
        }
        return (sumOdd - sumEven);
    }

    public static void main(String[] args) {
        Node a = new Node(5);
        Node b = new Node(2);
        Node c = new Node(6);
        Node d = new Node(1);
        Node e = new Node(4);
        Node f = new Node(8);
        Node g = new Node(3);
        Node h = new Node(7);
        Node i = new Node(9);

        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        c.right = e;
        e.left = g;
        c.right = f;
        f.left = h;
        f.right = i;

        System.out.println(differenceBetweenSums(a));
    }
}
