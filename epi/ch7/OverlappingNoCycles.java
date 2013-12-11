class Node<T> {
    Node<T> next;
    T value;

    Node(T value) { this.value = value; }

    @Override
    public String toString() {
        return String.format("{value=%s}", value);
    }

    public int size() {
        Node<T> current = this;
        int returnValue = 0;
        while (current != null) {
            returnValue++;
            current = current.next;
        }
        return returnValue;
    }
}

class OverlappingNoCycles {
    public static <T> void advanceByK(Node<T> current, int k) {
        for (int i = 0; i < k; k++) {
            if (current == null) return;
            current = current.next;
        }
    }

    public static <T> Node<T> findOverlap(Node<T> h1, Node<T> h2) {        
        if ((h1 == null) || (h2 == null)) return null; 

        /* this is O(n+m). Note that overlapping lists must have the same
           tail. */
        int h1Size = h1.size(), h2Size = h2.size();
        if (h1Size < h2Size)
            advanceByK(h2, h2Size - h1Size);
        else 
            advanceByK(h1, h1Size - h2Size);
        while (h1 != null && h2 != null && h1 != h2) {
            h1 = h1.next;
            h2 = h2.next;
        }
        return h1;

        /* this is O(nm)...we can do better and do it in O(n+m).
        Node<T> h1Current = h1;
        Node<T> h2Current = h2;
        while (h1Current != null) {
            while (h2Current != null) {
                if (h1Current == h2Current) return h1Current;
                h2Current = h2Current.next;
            }
            h1Current = h1Current.next;
            h2Current = h2;
        }
        return null;
        */
    }

    public static void main(String[] args) {
        Node<Integer> a = new Node<>(1);
        Node<Integer> b = new Node<>(2);
        Node<Integer> c = new Node<>(3);
        Node<Integer> d = new Node<>(4);
        Node<Integer> e = new Node<>(5);
        Node<Integer> f = new Node<>(6);

        a.next = b;
        b.next = e;
        c.next = d;
        d.next = e;
        e.next = f;

        Node<Integer> h1 = a;
        Node<Integer> h2 = c;

        System.out.println(findOverlap(h1, h2));
    }
}
