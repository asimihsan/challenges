import unittest


def lcs_recursive_basic(s, t):
    if len(s) == 0 or len(t) == 0:
        return ''
    if s[0] == t[0]:
        return s[0] + lcs_recursive_basic(s[1:], t[1:])
    left = lcs_recursive_basic(s[1:], t)
    right = lcs_recursive_basic(s, t[1:])
    if len(left) > len(right):
        return left
    else:
        return right


def lcs_dp(s, t):
    A = [[None] * (len(t) + 1)] * (len(s) + 1)
    for i in xrange(len(s), -1, -1):
        for j in xrange(len(t), -1, -1):
            if i == len(s) or j == len(t):
                A[i][j] = ''
            elif s[i] == t[j]:
                A[i][j] = s[i] + A[i+1][j+1]
            else:
                left = A[i+1][j]
                right = A[i][j+1]
                if len(left) > len(right):
                    A[i][j] = left
                else:
                    A[i][j] = right
    return A[0][0]


class LCSTestCase(object):
    def test1(self):
        self.assertEqual(self.lcs('ABCDE', 'AFCGE'), 'ACE')

    def test_empty(self):
        self.assertEqual(self.lcs('', ''), '')


class LCSRecursiveBasic(unittest.TestCase, LCSTestCase):
    def __init__(self, *args, **kwargs):
        super(LCSRecursiveBasic, self).__init__(*args, **kwargs)
        self.lcs = lcs_recursive_basic


class LCSDP(unittest.TestCase, LCSTestCase):
    def __init__(self, *args, **kwargs):
        super(LCSDP, self).__init__(*args, **kwargs)
        self.lcs = lcs_dp

if __name__ == '__main__':
    unittest.main()
