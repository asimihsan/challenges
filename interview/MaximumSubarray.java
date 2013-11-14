class MaximumSubarray {
    public static int maximumSubarray(int[] array) {
        Integer maximumSoFar = null;
        Integer maxSum = maximumSoFar;
        for (int i : array) {
            maximumSoFar = (maximumSoFar == null) ? i : Math.max(i, maximumSoFar + i);
            maxSum = (maxSum == null) ? maximumSoFar : Math.max(maxSum, maximumSoFar);
        }
        return maxSum;
    }

    public static void main(String[] args) {
        System.out.println(maximumSubarray(
            new int[] { -1, 5, 6, -2, 20, -50, 4 }));
    }
}