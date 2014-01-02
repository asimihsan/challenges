import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class PriorityQueue<T> {
    class Node {
        int priority;
        T data;
        Node(T data, int priority) {
            this.data = data;
            this.priority = priority;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            Node n = (Node)o;
            return (n.data.equals(data));
        }

        @Override
        public int hashCode() {
            return data.hashCode();
        }

        @Override
        public String toString() {
            return String.format("{data=%s, priority=%s}", data, priority);
        }
    }
    List<Node> array = new ArrayList<>();
    Map<Node, Integer> lookup = new HashMap<>();

    @Override
    public String toString() {
        return String.format("{array=%s, lookup=%s}", array, lookup);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return array.size();
    }

    public void add(T data, int priority) {
        Node node = new Node(data, priority);
        array.add(node);
        lookup.put(node, array.size() - 1);
        bubbleUp(array.size() - 1);
    }

    public T remove() {
        if (array.size() == 0) return null;
        Node returnValue = array.get(0);
        swap(0, array.size() - 1);
        array.remove(array.size() - 1);
        lookup.remove(returnValue);
        bubbleDown(0);
        return returnValue.data;
    }

    public void adjust(T data, int newPriority) {
        Node key = new Node(data, -1);
        if (!lookup.containsKey(key)) return;
        int i = lookup.get(key);
        Node node = array.get(i);
        int oldPriority = node.priority;
        node.priority = newPriority;
        if (newPriority > oldPriority)
            bubbleDown(i);
        else
            bubbleUp(i);
    }

    public boolean contains(T data) {
        Node key = new Node(data, -1);
        return lookup.containsKey(key);
    }

    private void swap(int i, int j) {
        Node ni = array.get(i);
        Node nj = array.get(j);
        lookup.put(ni, j);
        lookup.put(nj, i);
        Collections.swap(array, i, j);
    }

    private int parentIndex(int i) {
        if ((i % 2) == 0)
            return i / 2 - 1;
        else
            return i / 2;
    }

    private int leftIndex(int i) {
        return 2 * i + 1;
    }

    private int rightIndex(int i) {
        return 2 * i + 2;
    }

    private void bubbleUp(int i) {
        if (i == 0) return;
        Node ni = array.get(i);
        int parent = parentIndex(i);
        Node nparent = array.get(parent);
        if (nparent.priority > ni.priority) {
            swap(i, parent);
            bubbleUp(parent);
        }
    }

    private void bubbleDown(int i) {
        int left = leftIndex(i);
        int right = rightIndex(i);
        if ((left >= array.size() - 1) || (right >= array.size() - 1))
            return;
        Node nleft = array.get(left);
        Node nright = array.get(right);        
        int dominant = (nleft.priority > nright.priority) ? right : left;
        Node ndominant = (nleft.priority > nright.priority) ? nright : nleft;
        Node ni = array.get(i);
        if (ndominant.priority > ni.priority) {
            swap(i, dominant);
            bubbleDown(dominant);
        }
    }
}


class Graph<T> {
    Map<T, Map<T, Integer>> adj = new HashMap<>();

    public void addEdge(T from, T to, Integer weight) {
        if (!adj.containsKey(from))
            adj.put(from, new HashMap<T, Integer>());
        adj.get(from).put(to, weight);
    }

    public Collection<T> getNeighbours(T node) {
        if (!adj.containsKey(node))
            return Collections.emptyList();
        return adj.get(node).keySet();
    }

    public Collection<T> getNodes() {
        return adj.keySet();
    }

    public T getRandomNode() {
        for (T node : getNodes())
            return node;
        return null;
    }

    public Integer getWeight(T from, T to) {
        if (!adj.containsKey(from))
            return null;
        if (!adj.get(from).containsKey(to))
            return null;
        return adj.get(from).get(to);
    }

    @Override
    public String toString() {
        return adj.toString();
    }

    public int size() {
        return adj.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}

class PrimsMST<T> {
    Map<T, Integer> key = new HashMap<>();
    Map<T, T> pred = new HashMap<>();

    PrimsMST(Graph<T> g) {
        for (T v : g.getNodes()) {
            key.put(v, Integer.MAX_VALUE);
            pred.put(v, null);
        }
        key.put(g.getRandomNode(), 0);
        PriorityQueue<T> pq = new PriorityQueue<T>();
        for (T v : g.getNodes()) {
            pq.add(v, key.get(v));
        }

        while (!pq.isEmpty()) {
            T u = pq.remove();
            for (T v : g.getNeighbours(u)) {
                if (pq.contains(v)) {
                    int w = g.getWeight(u, v);
                    if (w < key.get(v)) {
                        pred.put(v, u);
                        key.put(v, w);
                        pq.adjust(v, w);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format("{pred=%s, key=%s}", pred, key);
    }    
}

class Node {
    int row;
    int col;

    Node(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return String.format("{row=%s, col=%s}", row, col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node n = (Node)o;
        return (row == n.row) && (col == n.col);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + row;
        result = 31 * result + col;
        return result;
    }

    public int distanceTo(Node other) {
        return Math.abs(row - other.row) + Math.abs(col - other.col);
    }
}

class HackerRankBotCleanLarge {
    static String getAction(String[] board, Node source, Node destination) {
        //System.out.println(String.format("source=%s, destination=%s", source, destination));
        if (board[source.row].charAt(source.col) == 'd') {
            return "CLEAN";
        }
        if (destination == null)
            throw new IllegalStateException("Can't decide move with destination as null");
        if (Math.abs(source.row - destination.row) < Math.abs(source.col - destination.col)) {
            if (source.col > destination.col) {
                return "LEFT";
            } else {
                return "RIGHT";
            }
        } else {
            if (source.row > destination.row) {
                return "UP";
            } else {
                return "DOWN";
            }
        }
    }

    static Graph<Node> newGraph(String[] board) {
        List<Node> nodes = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length(); col++) {
                if (board[row].charAt(col) != '-') {
                    nodes.add(new Node(row, col));
                }
            }
        }
        Graph<Node> g = new Graph<Node>();
        for (Node from : nodes) {
            for (Node to : nodes) {
                if (from.equals(to))
                    continue;
                g.addEdge(from, to, from.distanceTo(to));
            }
        }
        return g;
    }


    static void next_move(int posr, int posc, int dimh, int dimw, String[] board) {
        Node source = new Node(posr, posc);
        Graph<Node> g = newGraph(board);
        //System.out.println(String.format("graph: %s", g));
        PrimsMST<Node> mst = new PrimsMST<Node>(g);
        //System.out.println(String.format("mst: %s", mst));

        // Prims MST results in pred, a Hamiltonian path in the graph.
        // If there are only two nodes left the source may be the
        // _terminating_ point, but we know where we have to go (the
        // other node).
        Node next = mst.pred.get(source);
        if (next == null) {
            Collection<Node> neighbours = g.getNeighbours(source);
            if (neighbours.size() > 0)
                next = neighbours.iterator().next();
        }
        String action = getAction(board, source, next);
        System.out.println(action);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int [] pos = new int[2];
        int [] dim = new int[2];
        for(int i=0;i<2;i++) pos[i] = in.nextInt();
        for(int i=0;i<2;i++) dim[i] = in.nextInt();
        String board[] = new String[dim[0]];
        for(int i=0;i<dim[0];i++) board[i] = in.next();
        next_move(pos[0], pos[1], dim[0], dim[1], board);
    }
}