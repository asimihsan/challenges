import java.util.ArrayDeque;
import java.util.Deque;

class StackWithName<T> {
    private String name;
    private Deque<T> data = new ArrayDeque<>();
    StackWithName(String name) { this.name = name; }
    public String getName() { return this.name; }

    public void push(T value) { data.push(value); }
    public T pop() { return data.pop(); }
    public T peek() { return data.peek(); }
}

class TowersOfHanoi {
    public static void execute(int numberOfRings) {
        StackWithName<Integer> fromPole = new StackWithName<>("fromPole");
        StackWithName<Integer> toPole = new StackWithName<>("toPole");
        StackWithName<Integer> withPole = new StackWithName<>("withPole");
        for (int i = numberOfRings - 1; i >= 0; i--)
            fromPole.push(i);
        moveTower(numberOfRings, fromPole, toPole, withPole);
    }

    public static <T> void moveTower(int height, StackWithName<T> fromPole,
                                     StackWithName<T> toPole, StackWithName<T> withPole) {
        if (height == 0) return;
        moveTower(height - 1, fromPole, withPole, toPole);
        moveDisk(fromPole, toPole);
        moveTower(height - 1, withPole, toPole, fromPole);
    }

    public static<T> void moveDisk(StackWithName<T> fromPole, StackWithName<T> toPole) {
        System.out.println(String.format("moving disk %s from %s to %s",
                                         fromPole.peek(), fromPole.getName(), toPole.getName()));
        toPole.push(fromPole.pop());
    }

    public static void main(String[] args) {
        execute(3);
    }
}