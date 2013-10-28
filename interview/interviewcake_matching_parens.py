import unittest


def find_matching_paren(input, pos):
    cnt = 0
    for i in xrange(pos, len(input)):
        if input[i] == '(':
            cnt += 1
        elif input[i] == ')':
            cnt -= 1
        if cnt == 0:
            return i
    return None


class FindMatchingParenTestCase(unittest.TestCase):

    def test_1(self):
        self.assertEqual(find_matching_paren('i am (foo(foo))', 5), 14)

    def test_2(self):
        self.assertEqual(find_matching_paren('i am (foo(foo))', 9), 13)

if __name__ == '__main__':
    unittest.main()
