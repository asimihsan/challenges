import unittest


def get_duplicate_number(array):
    bitvector = 0
    for number in array:
        mask = 1 << number
        if bitvector & mask:
            return number
        bitvector |= mask
    assert(False)


class GetDuplicateNumberTestCase(unittest.TestCase):

    def test_1(self):
        self.assertEqual(get_duplicate_number([1, 5, 6, 3, 1]), 1)

if __name__ == '__main__':
    unittest.main()
