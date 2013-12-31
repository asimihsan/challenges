import itertools
import sys


def readline_ints():
    return [int(elem) for elem in sys.stdin.readline().strip().split()]

N = int(sys.stdin.readline().strip())
for i in xrange(N):
    _, K = readline_ints()
    A = readline_ints()
    B = readline_ints()
    A.sort()
    B.sort(reverse=True)
    if all(sum([a, b]) >= K for (a, b) in itertools.izip(A, B)):
        print 'YES'
    else:
        print 'NO'
