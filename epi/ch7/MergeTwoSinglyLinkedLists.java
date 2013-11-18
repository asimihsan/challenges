class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    T data;
    Node<T> next;
    Node(T data) { this.data = data; }

    @Override
    public String toString() {
        return String.format("{%s, %s}", data, next);
    }

    @Override
    public int compareTo(Node<T> o) {
        return data.compareTo(o.data);
    }
}

class MergeTwoSinglyLinkedLists {
    public static <T extends Comparable<T>> Node<T> merge(Node<T> L, Node<T> R) {
        if (L == null && R == null) return null;
        if (L == null) return R;
        if (R == null) return L;
        Node<T> head;
        if (L.compareTo(R) <= 0) {
            head = L;
            L = L.next;
        } else {
            head = R;
            R = R.next;
        }
        Node<T> curr = head;
        while (L != null || R != null) {
            if (R == null) {
                curr.next = L;
                curr = L;
                L = L.next;
            }
            else if (L == null) {
                curr.next = R;
                curr = R;
                R = R.next;
            }
            else {
                if (L.compareTo(R) <= 0) {
                    curr.next = L;
                    curr = L;
                    L = L.next;
                } else {
                    curr.next = R;
                    curr = R;
                    R = R.next;
                }
            }
        }
        return head;
    } 

    public static void main(String[] args) {
        Node<Integer> L = new Node<Integer>(2);
        L.next = new Node<Integer>(3);
        L.next.next = new Node<Integer>(5);
        L.next.next.next = new Node<Integer>(9);

        Node<Integer> R = new Node<Integer>(4);
        R.next = new Node<Integer>(6);
        R.next.next = new Node<Integer>(7);

        Node<Integer> M = merge(L, R);
        System.out.println(M);
    }
}