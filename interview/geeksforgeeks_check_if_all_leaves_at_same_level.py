class BinaryTree(object):
    def __init__(self, key):
        self.key = key
        self.left = None
        self.right = None


class Sentinel(object):
    pass
    

def are_leaves_same_level(tree):
    dequeue = [Sentinel(), tree]
    leaf_level = None
    current_level = 0
    while len(dequeue) != 0:
        current = dequeue.pop()
        if isinstance(current, Sentinel):
            if len(dequeue) == 0:
                break
            current_level += 1
            dequeue.insert(0, Sentinel())
            continue
        if current.right is None and current.left is None:
            # leaf node.
            if leaf_level is None:
                leaf_level = current_level
            elif leaf_level != current_level:
                return False
        if current.right is not None:
            dequeue.insert(0, current.right)
        if current.left is not None:
            dequeue.insert(0, current.left)
    return True
        


a = BinaryTree('a')
b = BinaryTree('b')
c = BinaryTree('c')
d = BinaryTree('d')
e = BinaryTree('e')

a.left = b
a.right = c
b.left = d
c.right = e

print are_leaves_same_level(a)
