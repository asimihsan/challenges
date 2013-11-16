class DutchFlagPartition {
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void print(int[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length - 1; i++)
            System.out.print(String.format("%s, ", array[i]));
        System.out.println(String.format("%s]", array[array.length - 1]));
    }

public static void parition(int[] A, int i) {
    int smaller = 0, equal = 0, larger = A.length - 1;
    int pivot = A[i];
    while (equal <= larger) {
        if (A[equal] < pivot)
            swap(A, smaller++, equal++);
        else if (A[equal] == pivot)
            equal++;
        else
            swap(A, equal, larger--);
    }
}

    public static void main(String[] args) {
        int[] array = {3, 2, -2, 3, 0, 5, 6, 3, 3, 1, 3};
        int pivot = 3;  // = 3
        // _unstable_ sort! but should be partitioned into thirds.
        
        parition(array, 3);
        print(array);
    }
}