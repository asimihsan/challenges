import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

class MinHeap<Key extends Comparable<Key>> {
    List<Key> array = new ArrayList<Key>();

    private static int parent(int k) { return k/2; }
    private static int leftChild(int k) { return 2*k; }
    private static int rightChild(int k) { return 2*k+1; }

    public int size() { return array.size(); }

    public void add(Key key) {
        array.add(key);
        bubbleUp(array.size() - 1);
    }

    public Key remove() {
        Key return_value = array.get(0);
        Collections.swap(array, 0, array.size() - 1);
        array.remove(array.size() - 1);
        bubbleDown(0);
        return return_value;
    }

    private void bubbleUp(int k) {
        while (k >= 0) {
            if (array.get(parent(k)).compareTo(array.get(k)) > 0) {
                Collections.swap(array, parent(k), k);
                k = parent(k);
            } else {
                break;
            }
        }
    }

    private void bubbleDown(int k) {
        while (true) {
            int left = leftChild(k), right = rightChild(k), dominant;
            if (left >= array.size())
                break; 
            if (right >= array.size())
                dominant = left;
            else if (array.get(left).compareTo(array.get(right)) < 0)
                dominant = left;
            else
                dominant = right;
            if (array.get(dominant).compareTo(array.get(k)) < 0) {
                Collections.swap(array, dominant, k);
                k = dominant;
            } else {
                break;
            }
        }
    }

    @Override
    public String toString() { return array.toString(); }
}

class Log implements Comparable<Log> {
    Integer currentTimestamp;
    String currentLine;
    boolean isFinished = false;
    private BufferedReader br;

    Log (String path) {
        FileSystem fs = FileSystems.getDefault();
        Path pathObject = fs.getPath(path);
        try {
            br = Files.newBufferedReader(pathObject, StandardCharsets.UTF_8);
            nextLine();
        } catch (IOException e) {
            e.printStackTrace(System.err);
            isFinished = true;
        }
    }

    public void nextLine() {
        if (isFinished == true)
            return;
        try {
            currentLine = br.readLine();
        } catch (IOException e) {
            e.printStackTrace(System.err);
            isFinished = true;
        }
        if (currentLine == null) {
            isFinished = true;
            return;
        }
        currentTimestamp = new Integer(currentLine.split(",")[0]);
    }

    public void close() {
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public int compareTo(Log o) {
        return currentTimestamp.compareTo(o.currentTimestamp);
    }

    @Override
    public String toString() {
        return currentLine;
    }
}

class MergeFiles {
    public static void main(String[] args) {
        String[] inputs = {"aapl.txt", "amzn.txt", "goog.txt", "ibm.txt"};
        String output = "merged.txt";

        FileSystem fs = FileSystems.getDefault();
        Path path = fs.getPath(output);
        try (
            BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
        ) {
            MinHeap<Log> heap = new MinHeap<Log>();
            for (String input : inputs) {
                Log log = new Log(input);
                heap.add(log);
            }
            while (heap.size() != 0) {
                Log log = heap.remove();
                bw.write(log.currentLine);
                bw.newLine();
                log.nextLine();
                if (!log.isFinished) {
                    heap.add(log);
                } else {
                    log.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        

        /*
        MinHeap<Integer> heap = new MinHeap<Integer>();
        for (int i : new int[] {5, 10, 3, 1, 6})
            heap.add(i);
        System.out.println(heap);
        System.out.println(heap.remove());
        System.out.println(heap);
        System.out.println(heap.remove());
        System.out.println(heap);
        */
    }
}