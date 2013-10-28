class Node(object):
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None

    def __str__(self):
        return '{value: %s, left: %s, right: %s}' % \
            (self.value, self.left, self.right)


def getBinaryTree(arr):
    if len(arr) == 0:
        return None
    if len(arr) == 1:
        return Node(arr[0])
    mid = (len(arr) - 1) // 2
    root = Node(arr[mid])
    root.left = getBinaryTree(arr[:mid])
    root.right = getBinaryTree(arr[mid+1:])
    return root


print getBinaryTree([1,2,3,4,5,6])

# root = Node(3), left = gBT([1,2]), right = gBT([4,5,6])
# gBT([1,2]), root = Node(2), left = gBT([1]), right=gBT([])
# gBT([4,5,6]), root = Node(5), left = gBT([5]), right = gBT([6])
