import random


def shuffle(arr):
    for i in xrange(len(arr)-1, 1, -1):
        j = random.randint(0, i)
        arr[j], arr[i] = arr[i], arr[j]

arr = [1, 2, 3, 4, 5]
shuffle(arr)
print arr
