import unittest


def is_in_sorted_array_iterative(arr, value):
    if len(arr) == 0:
        return False
    lo = 0
    hi = len(arr) - 1
    while lo != hi:
        mid = (hi + lo) // 2
        if value < arr[mid]:
            hi = max(mid - 1, lo)
        elif value > arr[mid]:
            lo = min(mid + 1, hi)
        else:
            return True
    return arr[lo] == value


def is_in_sorted_array_recursive(arr, value):
    if len(arr) == 0:
        return False
    if len(arr) == 1:
        return arr[0] == value
    index = (len(arr) - 1) // 2
    if arr[index] < value:
        return is_in_sorted_array_recursive(arr[index+1:], value)
    elif arr[index] > value:
        return is_in_sorted_array_recursive(arr[:index], value)
    else:
        return arr[index] == value


class IsInSortedArrayTestCase(object):

    def test_true_in_even_length(self):
        self.assertTrue(self.func([1, 2, 3, 4], 4))

    def test_false_in_even_length(self):
        self.assertFalse(self.func([1, 2, 3, 4], 5))

    def test_true_in_odd_length(self):
        self.assertTrue(self.func([1, 2, 3, 4, 5], 2))

    def test_false_in_odd_length(self):
        self.assertFalse(self.func([1, 2, 3, 4, 5], 0))

    def test_false_for_empty_array(self):
        self.assertFalse(self.func([], 0))


class IsInSortedArrayIterativeTestCase(unittest.TestCase,
                                       IsInSortedArrayTestCase):
    def __init__(self, *args, **kwargs):
        super(IsInSortedArrayIterativeTestCase, self).__init__(*args, **kwargs)
        self.func = is_in_sorted_array_iterative


class IsInSortedArrayRecursiveTestCase(unittest.TestCase,
                                       IsInSortedArrayTestCase):
    def __init__(self, *args, **kwargs):
        super(IsInSortedArrayRecursiveTestCase, self).__init__(*args, **kwargs)
        self.func = is_in_sorted_array_recursive

if __name__ == '__main__':
    unittest.main()
