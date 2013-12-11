"""
Given a Binary Tree, print left view of it. Left view of a Binary Tree is set
of nodes visible when tree is visited from left side. Left view of following
tree is 12, 10, 25.

          12
       /     \
     10       30
            /    \
          25      40
"""


class BinaryTree(object):
    def __init__(self, key):
        self.key = key
        self.left = None
        self.right = None
        
    def __str__(self):
        return '{key: %s}' % self.key

    def __repr__(self):
        return str(self)


class Sentinel(object):
    def __str__(self):
        return '{Sentinel}'

    def __repr__(self):
        return str(self)

    
def print_left_view(tree):
    queue = [Sentinel(), tree]
    level = []
    while len(queue) != 0:
        node = queue.pop()  # pops queue[-1]
        if isinstance(node, Sentinel):
            print level[0]
            level = []
            if len(queue) == 0:
                break
            queue.insert(0, Sentinel())
            continue
        if node.right is not None:
            queue.insert(0, node.right)
        if node.left is not None:
            queue.insert(0, node.left)
        level.insert(0, node)
        
a = BinaryTree('a')
b = BinaryTree('b')
c = BinaryTree('c')
d = BinaryTree('d')
e = BinaryTree('e')
f = BinaryTree('f')

a.left = b
a.right = c
b.left = d
c.left = e
c.right = f

print_left_view(a)
