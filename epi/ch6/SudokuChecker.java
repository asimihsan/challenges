class SudokuChecker {
    private static boolean checkColumn(int[][] array, int column) {
        boolean[] lookup = new boolean[10];
        for (int i = 0; i < 9; i++) {
            int value = array[i][column];
            if (value != 0 && lookup[value] == true)
                return false;
            lookup[value] = true;
        }
        return true;
    }
    
    private static boolean checkRow(int[][] array, int row) {
        boolean[] lookup = new boolean[10];
        for (int i = 0; i < 9; i++) {
            int value = array[row][i];
            if (value != 0 && lookup[value] == true)
                return false;
            lookup[value] = true;
        }
        return true;
    }
    
    private static boolean checkSubGrid(int[][] array, int index) {
        boolean[] lookup = new boolean[10];
        int rowStart = (index / 3) * 3, rowEnd = ((index / 3) * 3) + 2;
        int colStart = (index % 3) * 3, colEnd = ((index % 3) * 3) + 2;
        for (int i = rowStart, i <= rowEnd; i++) {
            for (int j = colStart, j <= colEnd; j++) {
                int value = array[i][j];
                if (value != 0 && lookup[value] == true)
                    return false;
                lookup[value] = true;
            }
        }
        return true;
    }

    public static boolean check(int[][] array) {
        for (int i = 0; i < 9; i++) {
            if (!(checkColumn(array, i)) return false;
            if (!(checkRow(array, i)) return false;
            if (!(checkSubGrid(array, i)) return false; 
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] array = new int[9][9];
        boolean isValid = check(array);
        System.out.println(isValid);
    }
}