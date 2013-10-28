def mergesort(arr):
    if len(arr) == 1:
        return (0, arr)
    left = arr[:len(arr)//2]
    (inversions_left, left) = mergesort(left)
    right = arr[len(arr)//2:]
    (inversions_right, right) = mergesort(right)

    inversions_merge = 0
    merged = []
    i = 0
    j = 0
    while True:
        if len(merged) == len(arr):
            inv = inversions_left + inversions_right + inversions_merge
            return (inv, merged)
        if i > len(left) - 1:
            merged.append(right[j])
            j += 1
        elif j > len(right) - 1:
            merged.append(left[i])
            i += 1
        else:
            if left[i] <= right[j]:
                merged.append(left[i])
                i += 1
            else:
                merged.append(right[j])
                j += 1
                inversions_merge += len(left) - i


def get_inversions(arr):
    return mergesort(arr)[0]

import sys
T = int(sys.stdin.readline().strip())
for i in xrange(T):
    sys.stdin.readline()
    print get_inversions([int(elem)
                          for elem in sys.stdin.readline().strip().split()])
