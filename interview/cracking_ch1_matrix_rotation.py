import pprint

arr = [[1,  2,  3,  4],
       [5,  6,  7,  8],
       [9,  10, 11, 12],
       [13, 14, 15, 16]]
pprint.pprint(arr)


def rotated(arr):
    N = len(arr)
    rarr = [[None for i in xrange(N)] for i in xrange(N)]
    for i in xrange(N):
        for j in xrange(N):
            rarr[i][j] = arr[N - j - 1][i]
    return rarr

pprint.pprint(rotated(arr))
