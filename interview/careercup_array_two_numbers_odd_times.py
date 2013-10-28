"""
You are given an integer array, where all numbers except for TWO numbers
appear even number of times.

Q: Find out the two numbers which appear odd number of times.
"""

import unittest


def two_numbers_odd_times(arr):
    def xor(a, b):
        return a ^ b

    xor_1 = reduce(xor, arr)
    bit_position = 1
    while xor_1 != 0:
        bit = xor_1 & 1
        if bit == 1:
            break
        xor_1 >> 1
        bit_position += 1

    mask = (1 << bit_position)
    xor_2 = reduce(xor, (elem for elem in arr if elem & mask != 0))
    xor_3 = reduce(xor, (elem for elem in arr if elem & mask == 0))
    return sorted([xor_2, xor_3])


class ArrayWithTwoNumbersOddTimesTestCase(unittest.TestCase):
    def test1(self):
        self.assertEqual(two_numbers_odd_times([1, 2, 1, 2, 3, 4]), [3, 4])


if __name__ == '__main__':
    unittest.main()
