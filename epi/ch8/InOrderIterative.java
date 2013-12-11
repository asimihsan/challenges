import java.util.ArrayDeque;
import java.util.Deque;

class Node<T> {
    Node<T> left;
    Node<T> right;
    T value;

    Node(T value) { this.value = value; }

    @Override
    public String toString() {
        return String.format("{value: %s}", value);
    }
}

class InOrderIterative {
    public static <T> void inOrderIterative(Node<T> node) {
        Deque<Node<T>> stack = new ArrayDeque<>();
        Node<T> current = node;
        while(!stack.isEmpty() || current != null) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                current = stack.pop();
                System.out.println(current);
                current = current.right;
            }
        }
    }

    public static void main(String[] args) {
        Node<Integer> a = new Node<>(19);
        Node<Integer> b = new Node<>(7);
        Node<Integer> c = new Node<>(3);
        Node<Integer> d = new Node<>(2);
        Node<Integer> e = new Node<>(5);
        Node<Integer> f = new Node<>(11);
        Node<Integer> g = new Node<>(17);
        Node<Integer> h = new Node<>(13);
        Node<Integer> i = new Node<>(43);
        Node<Integer> j = new Node<>(23);
        Node<Integer> k = new Node<>(37);
        Node<Integer> l = new Node<>(29);
        Node<Integer> m = new Node<>(31);
        Node<Integer> n = new Node<>(41);
        Node<Integer> o = new Node<>(47);
        Node<Integer> p = new Node<>(53);

        a.left = b;
        a.right = i;
        b.left = c;
        b.right = f;
        c.left = d;
        c.right = e;
        f.right = g;
        g.left = h;
        i.left = j;
        i.right = o;
        j.right = k;
        k.left = l;
        k.right = n;
        l.right = m;
        o.right = p;

        inOrderIterative(a);
    }
}