"""
Given an array, e.g.

[1, 5, -3, 2, 6, 3]

print all (a, b, c) distinct members of the array such that a + b + c = 0
"""


def three_sum_naive(arr):
    """Given a desired subset size k there is an O(n^k) solution. Just
    generate all possible combinations, sum them, and if 0 print it out.
    """
    for i in xrange(len(arr)):
        for j in xrange(i, len(arr)):
            for k in xrange(j, len(arr)):
                if arr[i] + arr[j] + arr[k] == 0:
                    print((arr[i], arr[j], arr[k]))


def three_sum_better(arr):
    """Given a desired subset size k there is a better O(n^(k-1)) solution.
    How? For the three-sum case we are looking for:

    a + b + c = 0

    this is equivalent to:

    c = -(a + b)

    So if we build up a dictionary of all sums of all pairs (a, b) in O(n^2)
    time, and then spend O(n) comparing the _negative_ of each array element
    to see if it's in the set, we can do the same problem in O(n^2).
    """

    sums_of_pairs = {}
    for i in xrange(len(arr)):
        for j in xrange(i, len(arr)):
            sums_of_pairs[arr[i]+arr[j]] = (i, j)
    for k in xrange(len(arr)):
        if -arr[k] in sums_of_pairs:
            pair = sums_of_pairs[-arr[k]]
            print((arr[pair[0]], arr[pair[1]], arr[k]))


arr = [1, 5, -3, 2, 6, 3]
three_sum_naive(arr)
print '-' * 79
three_sum_better(arr)
