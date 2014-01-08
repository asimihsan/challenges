#!/usr/bin/env python

import random
import unittest


def swap(A, i, j):
    A[i], A[j] = A[j], A[i]


def partition(A, lo, hi):
    pivot = random.randint(lo, hi)
    pivotValue = A[pivot]
    storeIndex = lo
    swap(A, pivot, hi)
    for i in xrange(lo, hi):
        if A[i] <= pivotValue:
            swap(A, i, storeIndex)
            storeIndex += 1
    swap(A, storeIndex, hi)
    return storeIndex


def kth_largest(A, k, lo=None, hi=None):
    """If A were sorted, return the (k+1)th largest element. If k is
    0 return the minimum. Does so in O(n) average time."""
    if lo is None:
        lo = 0
    if hi is None:
        hi = len(A) - 1
    while lo <= hi:
        i = partition(A, lo, hi)
        if i == k - 1:
            return A[i]
        elif i < k - 1:
            lo = i + 1
        else:
            hi = i - 1
    return A[lo]


class KthLargestTestCase(unittest.TestCase):
    def test_1(self):
        self.assertEqual(kth_largest([5, -1, 0, 6, 3, 4], 2), 0)

    def test_2(self):
        self.assertEqual(kth_largest([1], 0), 1)

    def test_3(self):
        self.assertEqual(kth_largest([1, 1, 1, 1, 1, 1], 3), 1)


if __name__ == "__main__":
    unittest.main()
