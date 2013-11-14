import functools


@functools.total_ordering
class Node(object):
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None
        
    def __lt__(self, other):
        return self.value < other.value
        
    def __eq__(self, other):
        if not type(self) is type(other):
            return False
        return self.value == other.value
        
    def __str__(self):
        return self.value
        
    def __repr__(self):
        return str(self)


class Sentinel(object):
    def __eq__(self, other):
        return type(self) is type(other)


def print_levels(root):
    sentinel = Sentinel()
    queue = [root, sentinel]
    level = []
    while True:
        current = queue.pop(0)  # pops left-most
        if current == sentinel:
            print ' '.join(str(node) for node in level)
            level = []
            if len(queue) == 0:
                break
            queue.append(sentinel)
            continue
        level.append(current)
        if current.left is not None:
            queue.append(current.left)
        if current.right is not None:
            queue.append(current.right)


a = Node('a')
b = Node('b')
c = Node('c')
d = Node('d')
e = Node('e')
a.left = b
a.right = c
c.left = d
c.right = e

print_levels(a)



def find_number_greater_than(root, value):
    if root.value > value:
        return root.value
    if root.right is None:
        return None
    return find_number_greater_than(root.right, value)
