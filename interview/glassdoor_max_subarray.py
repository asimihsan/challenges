"""

arr =   [1, 5, -10, 6, 1]
maxes = [1, 6, 6,   6, 7]

arr =   [0, -1, 1, 5, -1, 3, -7]
maxes = [0, 0,  1, 6,  5, 8, 1] 

"""

def max_subarray_sum(arr):
    max_so_far = max_sum = 0
    for x in arr:
        max_so_far = max(0, x + max_so_far)
        max_sum = max(max_so_far, max_sum)
    return max_sum

print max_subarray_sum([1, 5, -10, 6, 1])
print max_subarray_sum([0, -1, 1, 5, -1, 3, -7])
