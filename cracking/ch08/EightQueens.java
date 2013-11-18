class EightQueens {
    static int[] solve() {
        int[] queenCols = new int[8];
        solve(queenCols, 0);
        return queenCols;
    }

    static boolean isConflict(int[] queenCols, int currentRow) {
        if (currentRow == 0) return false;
        for (int i = 0; i < currentRow; i++) {
            for (int j = 0; j < currentRow; j++) {
                if (i == j)
                    continue;
                if (queenCols[i] == queenCols[j])
                    return true;
                if (Math.abs(queenCols[j] - queenCols[i]) == Math.abs(j - i))
                    return true;
            }
        }
        return false;
    }

    static boolean solve(int[] queenCols, int currentRow) {
        if (isConflict(queenCols, currentRow))
            return false;
        if (currentRow == queenCols.length)
            return true;
        for (int i = 0; i < queenCols.length; i++) {
            queenCols[currentRow] = i;
            if (solve(queenCols, currentRow + 1))
                return true;
        }
        return false;
    }

    static void print(int[] array) {
        for (int i = 0; i < array.length - 1; i++)
            System.out.print(String.format("%s ", array[i]));
        System.out.println(array[array.length - 1]);
    }

    public static void main(String[] args) {
        print(solve());
    }
}