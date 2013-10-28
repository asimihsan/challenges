arr = [[1,  2,  3],
       [0,  5,  0],
       [7,  8,  9],
       [10, 11, 12]]


def set_row_col_to_zero(arr):
    rows_to_zero = set()
    cols_to_zero = set()
    number_rows = len(arr)
    number_cols = len(arr[0])
    for i in xrange(number_rows):
        for j in xrange(number_cols):
            if arr[i][j] == 0:
                rows_to_zero.add(i)
                cols_to_zero.add(j)
    for i in xrange(number_rows):
        for j in xrange(number_cols):
            if i in rows_to_zero or j in cols_to_zero:
                arr[i][j] = 0
    return arr

print set_row_col_to_zero(arr)
