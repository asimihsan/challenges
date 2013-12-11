"""
Write a non-recursive algorithm to find the preorder successor of a given
node in a BST.

Note: Parent pointers are not given. If the node doesn't exist you should
return NULL.
"""


class Node(object):
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None

    def __str__(self):
        return '{value: %s, left: %s, right: %s}' % (self.value, self.left,
                                                     self.right)

    def __repr__(self):
        return str(self)


class BinarySearchTree(object):
    def __init__(self):
        self.root = None

    def __str__(self):
        return str(self.root)

    def get(self, value):
        current = self.root
        while current is not None:
            if current.value == value:
                return current
            elif value > current.value:
                current = current.right
            else:
                current = current.left
        return None

    def preorder_iter(self):
        trail = [self.root]
        while len(trail) != 0:
            node = trail.pop()
            if node is None:
                continue
            yield node
            trail.extend([node.right, node.left])

    def preorder_successor(self, value):
        node = self.get(value)
        assert(node is not None)
        if node.left is not None:
            return node.left
        if node.right is not None:
            return node.right
        trail = self._get_path_with_parents(node)
        for (node, parent) in trail[::-1]:
            if (parent is not None and
                    parent.left is not None and
                    parent.left == node and parent.right is not None):
                return parent.right
        return None

    def _get_path_with_parents(self, node):
        current = self.root
        path = [(current, None)]
        while current is not None:
            if node.value == current.value:
                return path
            if node.value > current.value:
                path.append((current.right, current))
                current = current.right
            else:
                path.append((current.left, current))
                current = current.left
        assert(False)

b = BinarySearchTree()
b.root = Node(4)
b.root.left = Node(2)
b.root.right = Node(6)
b.root.left.left = Node(1)
b.root.right.left = Node(5)
b.root.right.right = Node(7)

print(b)
print([node.value for node in b.preorder_iter()])

print('preorder_successor of 2: %s' % b.preorder_successor(2))
print('preorder_successor of 1: %s' % b.preorder_successor(1))
print('preorder_successor of 7: %s' % b.preorder_successor(7))
