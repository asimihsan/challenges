import unittest


def iterateSequence(arr, n):
    arr_copy = arr[:]
    for i in xrange(n):
        arr_copy = [j - i for (i, j) in zip(arr_copy, arr_copy[1:])]
    return sum(arr_copy)
    

class IterateSequenceTest(unittest.TestCase):
    def test1(self):
        self.assertEqual(iterateSequence([5, 6, 3, 9, -1], 1), -6)
        
    def test2(self):
        self.assertEqual(iterateSequence([5, 6, 3, 9, -1], 4), -38)


if __name__ == '__main__':
    unittest.main()
