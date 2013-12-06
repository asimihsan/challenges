import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


class PriorityQueue {
    class Node {
        private Integer identifier;
        private Double priority;

        Node(Integer identifier, Double priority) {
            this.identifier = identifier;
            this.priority = priority;
        }

        public void setPriority(Double priority) {  this.priority = priority; }
        public Double getPriority() { return priority; }
        public Integer getIdentifier() { return identifier; }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + identifier;
            return result;
        }

        @Override
        public String toString() {
            return String.format("{identifier=%s, priority=%s}", identifier, priority);
        }
    }

    class NodeMinComparator implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            return n1.getPriority().compareTo(n2.getPriority());
        }
    }

    Map<Integer, Integer> nodeIdentifierToHeapIndex = new HashMap<>();
    List<Node> heap = new ArrayList<>();
    Comparator<Node> comparator = new NodeMinComparator();

    @Override
    public String toString() {
        return String.format("heap=%s, nodeIdentifierToHeapIndex=%s",
                             heap,
                             nodeIdentifierToHeapIndex);
    }

    public void add(Integer identifier, Double priority) {
        Node x = new Node(identifier, priority);
        heap.add(x);
        bubbleUp(heap.size() - 1);
    }

    public Integer remove() {
        Integer returnValue = heap.get(0).getIdentifier();
        heap.set(0, heap.get(heap.size() - 1));
        nodeIdentifierToHeapIndex.remove(heap.get(heap.size() - 1).getIdentifier());
        heap.remove(heap.size() - 1);
        if (heap.size() > 0)
            bubbleDown(0);
        return returnValue;
    }

    public int size() { return heap.size(); }

    public boolean isEmpty() { return size() == 0; }

    public void adjust(Integer identifier, Double newPriority) {
        int index = nodeIdentifierToHeapIndex.get(identifier);
        Node x = heap.get(index);
        Double oldPriority = x.getPriority();
        x.setPriority(newPriority);
        if (newPriority > oldPriority)
            bubbleDown(index);
        else
            bubbleUp(index);
    }

    private void bubbleUp(int index) {
        nodeIdentifierToHeapIndex.put(heap.get(index).getIdentifier(),
                                      index);
        while (index != 0) {
            int parent = index / 2;
            if (comparator.compare(heap.get(parent), heap.get(index)) <= 0)
                break;
            Collections.swap(heap, parent, index);
            nodeIdentifierToHeapIndex.put(heap.get(index).getIdentifier(),
                                          index);
            nodeIdentifierToHeapIndex.put(heap.get(parent).getIdentifier(),
                                          parent);
            index = parent;
        }
    }

    private void bubbleDown(int index) {
        nodeIdentifierToHeapIndex.put(heap.get(index).getIdentifier(),
                                      index);
        while (2*index < heap.size()) {
            int left = 2 * index, right = 2 * index + 1, dominant;
            if (right >= heap.size())
                dominant = left;
            else
                if (comparator.compare(heap.get(left), heap.get(right)) < 0)
                    dominant = left;
                else
                    dominant = right;
            if (comparator.compare(heap.get(dominant), heap.get(index)) >= 0)
                break;
            Collections.swap(heap, dominant, index);
            nodeIdentifierToHeapIndex.put(heap.get(dominant).getIdentifier(),
                                          dominant);
            nodeIdentifierToHeapIndex.put(heap.get(index).getIdentifier(),
                                          index);
            index = dominant;
        }
    }

}

class Graph {
    private Map<Integer, Map<Integer, Double>> adj = new HashMap<>();

    Graph(String filepath) {
        FileSystem fs = FileSystems.getDefault();
        Path path = fs.getPath(filepath);
        try (
            BufferedReader br = Files.newBufferedReader(path,
                                                        StandardCharsets.UTF_8);
        ) {
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] elems = line.split("\\s");
                addEdge(new Integer(elems[0]),
                        new Integer(elems[1]),
                        new Double(elems[2]));
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public void addEdge(Integer from, Integer to, Double weight) {
        if (!(adj.containsKey(from)))
            adj.put(from, new HashMap<Integer, Double>());
        adj.get(from).put(to, weight);
    }

    public Collection<Integer> nodes() {
        return adj.keySet();
    }

    public Collection<Integer> neighbors(Integer node) {
        return adj.get(node).keySet();
    }

    public Double getWeight(Integer from, Integer to) {
        return adj.get(from).get(to);
    }

    @Override
    public String toString() {
        return String.format("adj: %s", adj);
    }
}

class DijkstraPQ {
    public static Map<Integer, Integer> singleSourceShortestPath(Graph g, Integer source, Integer dest) {
        Map<Integer, Double> dist = new HashMap<>();
        Map<Integer, Integer> pred = new HashMap<>();
        PriorityQueue pq = new PriorityQueue();
        for (Integer index : g.nodes()) {
            dist.put(index, Double.POSITIVE_INFINITY);
            pred.put(index, -1);
        }
        dist.put(source, 0.0);
        for (Integer index : g.nodes())
            pq.add(index, dist.get(index));
        while (!pq.isEmpty()) {
            Integer u = pq.remove();
            for (Integer v : g.neighbors(u)) {
                Double w = g.getWeight(u, v);
                Double newLen = dist.get(u) + w;
                if (newLen < dist.get(v)) {
                    pq.adjust(v, newLen);
                    dist.put(v, newLen);
                    pred.put(v, u);
                }
            }
        }
        return pred;
    }
}

class DijkstraPQTest {
    public static void main(String[] args) {
        Graph g = new Graph("graph2.txt");        
        System.out.println(g);
        System.out.println(DijkstraPQ.singleSourceShortestPath(g, 0, 3));
    }
}