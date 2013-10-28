import unittest


def counting_sort(arr, k):
    B = [0 for i in xrange(k)]
    for elem in arr:
        B[elem] += 1
    idx = 0
    for i in xrange(k):
        while B[i] > 0:
            arr[idx] = i
            idx += 1
            B[i] -= 1

    return arr


class CountingSortTest(unittest.TestCase):

    def test1(self):
        self.assertEqual(counting_sort([1, 4, 5, 2, 3, 1, 1, 1], 6),
                         [1, 1, 1, 1, 2, 3, 4, 5])

if __name__ == '__main__':
    unittest.main()
