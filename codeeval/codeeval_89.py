import sys


def memoize(f):
    class memodict(dict):
        def __getitem__(self, *key):
            return dict.__getitem__(self, key)

        def __missing__(self, key):
            ret = self[key] = f(*key)
            return ret

    return memodict().__getitem__


@memoize
def pass_sum(i=0, j=0):
    if i == len(triangle) - 1:
        return triangle[i][j]
    left = pass_sum(i+1, j)
    right = pass_sum(i+1, j+1)
    return max(left, right) + triangle[i][j]

global triangle
with open(sys.argv[1], 'r') as f_in:
    triangle = [[int(elem) for elem in line.strip().split()]
                for line in f_in.readlines()]
print pass_sum()
