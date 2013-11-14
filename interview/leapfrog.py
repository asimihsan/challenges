"""

from arr[0] to arr[len(arr)-1]

works!  [2, 2, -1, 1, 0]

OOB     [1, 1, 1, 100, 0]

cycle 1 [1, -1, 0, 0, 0]

cycle 2 [1, 0, 0, 0, 0]

"""

def leapfrog(arr):
    visited = {}
    i = 0
    maximum = len(arr) - 1
    while i != maximum:
        if visited.get(i, False) is True:
            return False
        visited[i] = True
        i += arr[i]
        if i < 0 or i > maximum:
            return False
    return True

print leapfrog([2, 2, -1, 1, 0])
print leapfrog([1, 0, 0, 0, 0])
print leapfrog([0])
