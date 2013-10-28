import unittest


def reverse(input):
    input = [ord(elem) for elem in input]
    i = 0
    j = len(input) - 1
    while i < j:
        input[i] = input[i] ^ input[j]  # i = i ^ j
        input[j] = input[j] ^ input[i]  # j = i
        input[i] = input[i] ^ input[j]  # i = j
        i += 1
        j -= 1
    return ''.join([chr(elem) for elem in input])


class TestReverse(unittest.TestCase):

    def test_1(self):
        self.assertEqual(reverse('foobar'), 'raboof')

    def test_2(self):
        self.assertEqual(reverse(''), '')

    def test_3(self):
        self.assertEqual(reverse('foo1bar'), 'rab1oof')

if __name__ == '__main__':
    unittest.main()
