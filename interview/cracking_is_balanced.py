def max_depth(node):
    if node is None:
        return 0
    return 1 + max(max_depth(node.left), max_depth(node.right))


def min_depth(node):
    if node is None:
        return 0
    return 1 + min(min_depth(node.left), min_depth(node.right))


def is_balanced(root):
    return max_depth(root) - min_depth(root) <= 1


class Node(object):
    def __init__(self, label, left=None, right=None):
        self.label = label
        self.left = left
        self.right = right

    def __str__(self):
        return '{label: %s, left: %s, right: %s}' % \
            (self.label, self.left, self.right)

    def __repr__(self):
        return str(self)


e = Node('e')
g = Node('g')
d = Node('d', e, g)
c = Node('c', d)
b = Node('b', c)
f = Node('f')
a = Node('a', b, f)

print is_balanced(a)


d = Node('d')
b = Node('b', d)
e = Node('e')
c = Node('c', None, e)
a = Node('a', b, c)

print is_balanced(a)
