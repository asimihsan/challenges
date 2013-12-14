class Node(object):
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None


def is_bst(node, min_value, max_value):
    if node is None:
        return True
    if node.left is None and node.right is None:
        return True
    if node.value < min_value or node.value > max_value:
        return False
    return (is_bst(node.left, min_value, node.value) and
            is_bst(node.right, node.value, max_value))


a = Node(6)
b = Node(3)
c = Node(7)
d = Node(2)
e = Node(59)

a.left = b
a.right = c
a.left.left = d
a.left.right = e

print is_bst(a, a.value, a.value)

