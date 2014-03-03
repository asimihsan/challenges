import java.util.ArrayDeque;
import java.util.Deque;

class WindowAverage {
    private Deque<Integer> data;
    private int n;
    private double mean = 0.0;

    WindowAverage(int n) {
        this.n = n;
        data = new ArrayDeque<Integer>(n);
        for (int i = 0; i < n; i++) {
            data.addLast(0);
        }
    }

    public double add(int x) {
        double first = (double)data.removeFirst();
        data.addLast(x);
        mean = mean - first/n + ((double)x)/n;
        return mean;
    }
}

class WindowAverageTest {
    public static void main(String[] args) {
        WindowAverage w = new WindowAverage(2);
        int[] array = {5, 4, 7, 8};
        for (int i : array)
            System.out.println(w.add(i));
    }
}