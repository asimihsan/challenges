import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

class CodingForInterviewsNQueens {
    public static int[][] getPlacements(int n) {
        List<int[]> placements = new ArrayList<int[]>();
        int[] current = new int[n];
        return placements.toArray(new int[placements.size()][n]);
    }

    private static int[][] getPlacements(int[] current, int currentColumn) {
        
    }

    public static void printPlacements(int[][] placements) {
        System.out.println(String.format(
            "Number of placements is %d",placements.length));
        for (int i = 0; i < placements.length; i++)
            System.out.println(String.format(
                "Placement %d: %s", i, Arrays.toString(placements[i])));
    }

    public static void main(String[] args) {
        printPlacements(getPlacements(8));
    }
}