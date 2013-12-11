class Node(object):
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None


def get_sum(node):
    if node.left is None and node.right is None:
        return node.value
    children = [elem for elem in [node.left, node.right]
                if elem is not None]
    return node.value + sum(get_sum(child) for child in children)


def is_valid_sum_tree(node):
    if node.left is None and node.right is None:
        return True
    if node.left is not None and not is_valid_sum_tree(node.left):
        return False
    if node.right is not None and not is_valid_sum_tree(node.right):
        return False
    children = [elem for elem in [node.left, node.right]
                if elem is not None]
    return node.value == sum(get_sum(child) for child in children)


a = Node(26)
b = Node(10)
c = Node(3)
d = Node(4)
e = Node(6)
f = Node(3)

a.left = b
a.right = c
b.left = d
b.right = e
c.right = f

print is_valid_sum_tree(a)
