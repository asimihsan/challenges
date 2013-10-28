import itertools
import sys

sys.stdin.readline()

children = map(int, (line.strip() for line in sys.stdin))
minimum_left = [1] * len(children)
minimum_right = [1] * len(children)
for i in xrange(len(children) - 2, -1, -1):
    if children[i] > children[i+1]:
        minimum_right[i] = 1 + minimum_right[i+1]
for i in xrange(0, len(children) - 1):
    if children[i+1] > children[i]:
        minimum_left[i+1] = 1 + minimum_left[i]
candies = [max([l, r])
           for (l, r) in itertools.izip(minimum_left, minimum_right)]
print sum(candies)
