class Node(object):
    def __init__(self, value, left=None, right=None):
        self.value = value
        self.left = left
        self.right = right

    def __str__(self):
        return '{value: %s}' % self.value

    def __repr__(self):
        return str(self)


def get_level_lists(root):
    levels = [[root]]
    while True:
        old_level = levels[-1]
        new_level = []
        for node in old_level:
            if node.left is not None:
                new_level.append(node.left)
            if node.right is not None:
                new_level.append(node.right)
        if len(new_level) == 0:
            break
        levels.append(new_level)
    return levels

a = Node(1)
b = Node(4)
c = Node(2, a)
d = Node(5, b)
e = Node(3, c, d)

print get_level_lists(e)
