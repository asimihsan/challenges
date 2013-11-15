import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

class KTreeNode {
    Integer value;
    Collection<KTreeNode> children = new ArrayList<KTreeNode>();

    KTreeNode() { }
    KTreeNode(Integer value) { this.value = value; }

    @Override
    public String toString() {
        return value.toString();
    }
}

class LevelOrderTreePrint {
    public static void levelOrderTreePrint(KTreeNode root) {
        KTreeNode sentinel = new KTreeNode();
        Queue<KTreeNode> queue = new ArrayDeque<KTreeNode>();
        List<KTreeNode> currentLevel = new ArrayList<KTreeNode>();

        Collections.addAll(queue, root, sentinel);
        while (true) {
            KTreeNode currentNode = queue.remove();
            if (currentNode == sentinel) {
                for (KTreeNode node : currentLevel)
                    System.out.print(String.format("%s ", node));
                System.out.println();
                if (queue.size() == 0) break;
                queue.add(sentinel);
                currentLevel.clear();
            } else {
                currentLevel.add(currentNode);
                queue.addAll(currentNode.children);
            }
        }
    }

    public static void main(String[] args) {
        /**
         *        3
         *        +
         *     +--+---->
         *     9      20
         *     +       +
         *     +    <--++----->
         *     4   15    7    8 
         */
        KTreeNode a = new KTreeNode(3);
        KTreeNode b = new KTreeNode(9);
        KTreeNode c = new KTreeNode(20);
        KTreeNode d = new KTreeNode(15);
        KTreeNode e = new KTreeNode(7);
        KTreeNode f = new KTreeNode(8);
        KTreeNode g = new KTreeNode(4);
        Collections.addAll(a.children, b, c);
        Collections.addAll(b.children, g);
        Collections.addAll(c.children, d, e, f);

        levelOrderTreePrint(a);
    }
}